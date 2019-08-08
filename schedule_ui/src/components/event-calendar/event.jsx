import React from "react";
import PropTypes from "prop-types";

export function Event({ event, rightOffset, color, onClick }) {
  const style = {
    background: color,
    width: (event.duration + 1) * 60 + "px",
    right: rightOffset + "px",
  };
  return (
    <div title={event.note} className="event" style={style} onClick={onClick} />
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
