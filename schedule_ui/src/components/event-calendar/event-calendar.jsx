import React, { Fragment, useState } from "react";
import PropTypes from "prop-types";
import { UnitEventRow } from "./unit-event-row";
import { EventPopup } from "./event-popup";
import { createEvent, updateEvent, removeEvent } from "helpers/api";
import { isDate, addDays } from "date-fns";

import "./event-calendar.scss";

const DEFAULT_FORM_DATA = {
  dateFrom: null,
  duration: null,
  eventId: null,
  eventTypeId: "",
  note: "",
  unitId: null,
};

export function EventCalendar({
  range,
  unitGroups,
  onUnitsUpdate,
  eventTypes,
  operationalDate,
}) {
  const [isFormOpen, toggle] = useState(false);
  const [formType, setFormType] = useState("create");
  const [unit, setUnit] = useState({});

  const [defaultFormData, setDefaultFormData] = useState(DEFAULT_FORM_DATA);

  const onEventCreate = formData => createEvent(formData).then(onUnitsUpdate);
  const onEventUpdate = formData => updateEvent(formData).then(onUnitsUpdate);
  const onEventRemove = eventId => removeEvent(eventId).then(onUnitsUpdate);

  const toggleCreateForm = (unit, dateFrom) => {
    unit && setUnit(unit);

    const eventTypeId = eventTypes[0].typeId;
    const duration = unit.eventTypeDurations
      ? unit.eventTypeDurations[defaultFormData.eventTypeId] || 1
      : 1;
    const dateTo = isDate(dateFrom) ? addDays(dateFrom, duration) : null;

    isFormOpen
      ? setDefaultFormData(DEFAULT_FORM_DATA)
      : setDefaultFormData({
          ...DEFAULT_FORM_DATA,
          unitId: unit.unitId,
          dateTo,
          dateFrom,
          duration,
          eventTypeId,
        });

    setFormType("create");
    toggle(!isFormOpen);
  };

  const toggleEditForm = (unit, event) => {
    if (!isFormOpen) {
      setFormType("edit");
      setUnit(unit);

      const dateFrom = new Date(event.dateFrom);
      const dateTo = addDays(dateFrom, event.duration);

      setDefaultFormData({
        ...event,
        dateFrom,
        dateTo,
      });
    } else {
      setDefaultFormData(DEFAULT_FORM_DATA);
    }
    toggle(!isFormOpen);
  };

  return (
    <Fragment>
      {unitGroups.map((group, i) => (
        <UnitEventRow
          key={i}
          range={range}
          unitGroup={group}
          openCreateForm={toggleCreateForm}
          openEditForm={toggleEditForm}
          eventTypes={eventTypes}
          operationalDate={operationalDate}
        />
      ))}
      <EventPopup
        type={formType}
        title={unit.title}
        isOpen={isFormOpen}
        toggle={toggleEditForm}
        eventTypeDurations={unit.eventDuration}
        onEventSubmit={formType === "create" ? onEventCreate : onEventUpdate}
        defaultFormData={defaultFormData}
        eventTypes={eventTypes}
        onEventRemove={onEventRemove}
      />
    </Fragment>
  );
}

EventCalendar.propTypes = {
  range: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string,
      days: PropTypes.arrayOf(PropTypes.object),
    })
  ),
  unitGroups: PropTypes.arrayOf(PropTypes.arrayOf(PropTypes.object)), //will change it to PropTypes.shape after BE fixes
  eventTypes: PropTypes.arrayOf(
    PropTypes.shape({
      color: PropTypes.string,
      description: PropTypes.string,
      typeId: PropTypes.number,
    })
  ),
  onUnitsUpdate: PropTypes.func,
  operationalDate: PropTypes.instanceOf(Date),
};

EventCalendar.defaultProps = {
  range: [],
  unitGroups: [],
  eventTypes: [],
};
