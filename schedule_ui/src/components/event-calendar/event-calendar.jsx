import React, { useEffect } from "react";
import PropTypes from "prop-types";
import { useDispatch, useSelector } from "react-redux";
import { openEventForm } from "redux/actions/forms";
import { getAuthData } from "redux/selectors/auth";
import { getEventTypesSelector } from "redux/selectors/event-types";
import { fetchEventTypes } from "redux/actions/event-types";
import { UnitEventRow } from "./unit-event-row";
import { EventPopup } from "./event-popup";
import { MANAGE_EVENTS } from "constants/permissions";
import { checkPermission } from "utils/permissions";
import { isDate, addDays, differenceInDays } from "date-fns";

import "./event-calendar.scss";

export function EventCalendar({ range, units }) {
  const dispatch = useDispatch();
  const eventTypes = useSelector(getEventTypesSelector);
  const { role } = useSelector(getAuthData);

  const isManageAble = checkPermission(role, MANAGE_EVENTS);

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

  const onCellClick = e => {
    const { unitId, date } = e.target.dataset;
    if (isManageAble && unitId && date) {
      const unit = units.find(unit => unit.unitId === +unitId);

      toggleCreateForm(unit, new Date(date));
    }
  };

  useEffect(() => {
    dispatch(fetchEventTypes());
  }, [dispatch]);

  return (
    <div onClick={onCellClick}>
      {units.map((unit, i) => (
        <UnitEventRow
          key={i}
          range={range}
          unit={unit}
          openCreateForm={toggleCreateForm}
          openEditForm={toggleEditForm}
          eventTypes={eventTypes}
          isManageAble={isManageAble}
        />
      ))}
      <EventPopup />
    </div>
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
};

EventCalendar.defaultProps = {
  range: [],
  units: [],
};
