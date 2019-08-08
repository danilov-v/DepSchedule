import React, { Fragment, useState } from "react";
import PropTypes from "prop-types";
import { UnitEventRow } from "./unit-event-row";
import { EventPopup } from "./event-popup";
import { createEvent, updateEvent, removeEvent } from "helpers/api";

import "./unit-calendar.scss";

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
}) {
  const [isCreateFormOpen, toggleCreate] = useState(false);
  const [isEditFormOpen, toggleEdit] = useState(false);
  const [unit, setUnit] = useState({});

  const [defaultFormData, setDefaultFormData] = useState(DEFAULT_FORM_DATA);

  const onEventCreate = formData => createEvent(formData).then(onUnitsUpdate);
  const onEventUpdate = formData => updateEvent(formData).then(onUnitsUpdate);
  const onEventRemove = eventId => removeEvent(eventId).then(onUnitsUpdate);

  const toggleCreateForm = (unit, dateFrom) => {
    unit && setUnit(unit);

    isCreateFormOpen
      ? setDefaultFormData(DEFAULT_FORM_DATA)
      : setDefaultFormData({
          ...DEFAULT_FORM_DATA,
          unitId: unit.unitId,
          dateFrom,
          eventTypeId: eventTypes[0].typeId,
        });
    toggleCreate(!isCreateFormOpen);
  };

  const toggleEditForm = (unit, event) => {
    unit && setUnit(unit);

    isEditFormOpen
      ? setDefaultFormData(DEFAULT_FORM_DATA)
      : setDefaultFormData({
          ...event,
          dateFrom: new Date(event.dateFrom),
        });

    toggleEdit(!isEditFormOpen);
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
        />
      ))}
      <EventPopup
        type="create"
        title={unit.title}
        isOpen={isCreateFormOpen}
        toggle={toggleCreateForm}
        eventTypeDurations={unit.eventDuration}
        onSubmit={onEventCreate}
        defaultFormData={defaultFormData}
        eventTypes={eventTypes}
      />
      <EventPopup
        type="edit"
        title={unit.title}
        isOpen={isEditFormOpen}
        toggle={toggleEditForm}
        eventTypeDurations={unit.eventDuration}
        onSubmit={onEventUpdate}
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
};

EventCalendar.defaultProps = {
  range: [],
  unitGroups: [],
  eventTypes: [],
};
