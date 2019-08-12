import React, { Fragment } from "react";
import PropTypes from "prop-types";
import { UncontrolledTooltip } from "reactstrap";

export function Event({ event, rightOffset, color, title, onClick }) {
  const style = {
    background: color,
    width: (event.duration + 1) * 35 + "px",
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
      <UncontrolledTooltip placement="bottom" target={"event-" + event.eventId}>
        {event.note}
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
