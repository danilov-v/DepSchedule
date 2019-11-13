import React from "react";
import PropTypes from "prop-types";
import { Row } from "reactstrap";
import { differenceInDays, isSameDay } from "date-fns";
import { constant } from "lodash";
import { getAllDatesFromRange } from "utils/date";
import { CELL_WIDTH } from "constants/calendar";
import { EventCell } from "./event-cell";
import { Event } from "./event";

const getOffset = (startDateCord, dateFrom) => {
  const diff = differenceInDays(
    new Date(dateFrom).setHours(0, 0, 0, 0),
    startDateCord.setHours(0, 0, 0, 0)
  );
  return diff * CELL_WIDTH;
};

export function UnitEventRow({
  range,
  unit,
  eventTypes,
  openEditForm,
  isManageAble,
}) {
  const allDates = getAllDatesFromRange(range);
  const startDateCord = allDates[allDates.length - 1];

  return (
    <Row key={unit.unitId} noGutters className="event-row">
      {allDates.map(date => (
        <EventCell
          key={date.getTime()}
          unitId={unit.unitId}
          date={date}
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
            flag={unit.flag}
            onClick={
              isManageAble ? () => openEditForm(unit, event) : constant(null)
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
  openEditForm: PropTypes.func,
  isManageAble: PropTypes.bool,
};

UnitEventRow.defaultProps = {
  range: [],
  unitGroup: [],
};
