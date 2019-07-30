import React from "react";
import PropTypes from "prop-types";

import "./unit-event-row.scss";

export function EventCell({ eventDate, eventOwnerId, onClick }) {
  return (
    <div
      id={eventOwnerId}
      className="event-cell"
      onClick={() => {
        onClick && onClick(eventOwnerId, eventDate);
      }}
    ></div>
  );
}

EventCell.propTypes = {
  eventDate: PropTypes.instanceOf(Date),
  eventOwnerId: PropTypes.number,
  onClick: PropTypes.func,
};
