import React, { Fragment, useEffect } from "react";
import PropTypes from "prop-types";
import { useDispatch, useSelector } from "react-redux";
import { openEventForm } from "redux/actions/forms";
import { getEventTypesSelector } from "redux/selectors/event-types";
import { fetchEventTypes } from "redux/actions/event-types";
import { UnitEventRow } from "./unit-event-row";
import { EventPopup } from "./event-popup";
import { isDate, addDays, differenceInDays } from "date-fns";

import "./event-calendar.scss";

export function EventCalendar({ range, units }) {
  const dispatch = useDispatch();
  const eventTypes = useSelector(getEventTypesSelector);

  const toggleCreateForm = (unit, dateFrom) => {
    const eventTypeId = eventTypes[0].typeId;
    const duration = unit.eventDuration
      ? unit.eventDuration[eventTypeId] || 1
      : 1;
    const dateTo = isDate(dateFrom) ? addDays(dateFrom, duration) : null;

    dispatch(
      openEventForm({
        formData: {
          unitId: unit.unitId,
          unitTitle: unit.title,
          unitEventTypeDuration: unit.eventDuration,
          dateTo,
          dateFrom,
          duration,
          eventTypeId,
          planned: unit.planned,
        },
      })
    );
  };

  const toggleEditForm = (unit, event) => {
    const dateFrom = new Date(event.dateFrom);
    const dateTo = new Date(event.dateTo);
    const duration = differenceInDays(dateTo, dateFrom);

    dispatch(
      openEventForm({
        formData: {
          ...event,
          unitTitle: unit.title,
          dateFrom,
          dateTo,
          duration,
        },
        isEdit: true,
      })
    );
  };

  useEffect(() => {
    dispatch(fetchEventTypes());
  }, [dispatch]);

  return (
    <Fragment>
      {units.map((unit, i) => (
        <UnitEventRow
          key={i}
          range={range}
          unit={unit}
          openCreateForm={toggleCreateForm}
          openEditForm={toggleEditForm}
          eventTypes={eventTypes}
        />
      ))}
      <EventPopup />
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
  units: PropTypes.arrayOf(
    PropTypes.shape({
      title: PropTypes.string,
      childUnit: PropTypes.array,
      eventDuration: PropTypes.object,
      events: PropTypes.array,
      parentId: PropTypes.number,
      unitId: PropTypes.number,
    })
  ),
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
  units: [],
  eventTypes: [],
};
