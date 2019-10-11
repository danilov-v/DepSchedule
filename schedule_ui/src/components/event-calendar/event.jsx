import React, { Fragment } from "react";
import PropTypes from "prop-types";
import { truncate } from "lodash";
import { UncontrolledTooltip } from "reactstrap";
import { CELL_WIDTH } from "constants/calendar";
import { differenceInDays } from "date-fns";

const getEventLength = ({ dateFrom, dateTo }) =>
  (differenceInDays(new Date(dateTo), new Date(dateFrom)) + 1) * CELL_WIDTH +
  "px";

export function Event({ event, rightOffset, color, title, onClick }) {
  const style = {
    background: color,
    width: getEventLength(event),
    right: rightOffset + "px",
  };
  return (
    <Fragment>
      <div
        id={"event-" + event.eventId}
        className="event"
        style={style}
        onClick={onClick}
      >
        {title}
      </div>
      <UncontrolledTooltip
        placement="bottom-start"
        target={"event-" + event.eventId}
      >
        {truncate(event.note, {
          length: 250,
        })}
      </UncontrolledTooltip>
    </Fragment>
  );
}

Event.propTypes = {
  event: PropTypes.shape({
    dateFrom: PropTypes.string,
    duration: PropTypes.number,
    eventId: PropTypes.number,
    eventTypeId: PropTypes.number,
    note: PropTypes.string,
    unitId: PropTypes.number,
  }).isRequired,
  rightOffset: PropTypes.number,
  color: PropTypes.string,
  onClick: PropTypes.func.isRequired,
};
