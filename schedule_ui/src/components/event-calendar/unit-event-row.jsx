import React from "react";
import PropTypes from "prop-types";
import { Row } from "reactstrap";
import {
  differenceInDays,
  isWithinInterval,
  addDays,
  isSameDay,
} from "date-fns";
import { constant } from "lodash";
import { MANAGE_EVENTS } from "constants/permishions";
import { checkPermission } from "utils/permishions";
import { useAuth } from "components/auth-service/auth-service";
import { getAllDatesFromRange } from "utils/date";
import { EventCell } from "./event-cell";
import { Event } from "./event";

const isEventInDate = (date, events) =>
  events
    ? events.some(({ dateFrom, duration }) =>
        isWithinInterval(date, {
          start: new Date(dateFrom).setHours(0, 0, 0, 0),
          end: addDays(new Date(dateFrom), duration).setHours(0, 0, 0, 0),
        })
      )
    : false;

const getOffset = (startDateCord, dateFrom) => {
  const diff = differenceInDays(
    new Date(dateFrom).setHours(0, 0, 0, 0),
    startDateCord.setHours(0, 0, 0, 0)
  );
  return diff * 35;
};

export function UnitEventRow({
  range,
  unit,
  eventTypes,
  openCreateForm,
  openEditForm,
}) {
  const { getRole } = useAuth();
  const isManageAble = checkPermission(getRole(), MANAGE_EVENTS);
  const allDates = getAllDatesFromRange(range);
  const startDateCord = allDates[allDates.length - 1];

  return (
    <Row key={unit.unitId} noGutters className="event-row">
      {allDates.map(date => (
        <EventCell
          key={date.getTime()}
          onClick={
            isManageAble
              ? openCreateForm.bind(null, unit, date)
              : constant(null)
          }
          hasEvent={isEventInDate(date, unit.events)}
          marked={isSameDay(date, new Date())}
        />
      ))}
      {unit.events.map(event => {
        const { color, description } = eventTypes.find(
          type => type.typeId === event.eventTypeId
        ) || { color: "#000", description: "" };

        return (
          <Event
            key={event.eventId}
            event={event}
            rightOffset={getOffset(startDateCord, event.dateFrom)}
            color={color}
            title={description}
            onClick={
              isManageAble
                ? openEditForm.bind(null, unit, event)
                : constant(null)
            }
          />
        );
      })}
    </Row>
  );
}

UnitEventRow.propTypes = {
  range: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string,
      days: PropTypes.arrayOf(PropTypes.object),
    })
  ),
  eventTypes: PropTypes.arrayOf(
    PropTypes.shape({
      color: PropTypes.string,
      description: PropTypes.string,
      typeId: PropTypes.number,
    })
  ),
  unit: PropTypes.shape({
    title: PropTypes.string,
    childUnit: PropTypes.array,
    eventDuration: PropTypes.object,
    events: PropTypes.array,
    parentId: PropTypes.number,
    unitId: PropTypes.number,
  }),
  openCreateForm: PropTypes.func,
  openEditForm: PropTypes.func,
};

UnitEventRow.defaultProps = {
  range: [],
  unitGroup: [],
};
