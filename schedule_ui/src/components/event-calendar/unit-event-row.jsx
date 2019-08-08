import React from "react";
import PropTypes from "prop-types";
import { Row } from "reactstrap";
import { EventCell } from "./event-cell";
import { Event } from "./event";
import { getAllDatesFromRange } from "../../utils/date";
import { differenceInDays, isWithinInterval, addDays, subDays } from "date-fns";

const hasEventInDate = (date, events) =>
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
  return diff * 60;
};

export function UnitEventRow({
  range,
  unitGroup,
  eventTypes,
  openCreateForm,
  openEditForm,
}) {
  const allDates = getAllDatesFromRange(range);
  const startDateCord = allDates[allDates.length - 1];

  return unitGroup.map(unit => (
    <Row key={unit.unitId} noGutters className="event-row">
      {unit.events
        ? unit.events.map(event => {
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
                onClick={openEditForm.bind(null, unit, event)}
              />
            );
          })
        : []}
      {allDates.map(date => (
        <EventCell
          key={date.getTime()}
          onClick={openCreateForm.bind(null, unit, date)}
          hasEvent={hasEventInDate(date, unit.events)}
        />
      ))}
    </Row>
  ));
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
  unitGroup: PropTypes.arrayOf(PropTypes.object), //will change it to PropTypes.shape after BE fixes
  openCreateForm: PropTypes.func,
  openEditForm: PropTypes.func,
};

UnitEventRow.defaultProps = {
  range: [],
  unitGroup: [],
};
