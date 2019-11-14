import React from "react";
import PropTypes from "prop-types";
import { truncate, get } from "lodash";
import { UncontrolledPopover, PopoverBody } from "reactstrap";
import { CELL_WIDTH } from "constants/calendar";
import { differenceInDays } from "date-fns";
import { FLAGS_MAP, FLAG_LOCATIONS } from "constants/flags";

const getEventLength = ({ dateFrom, dateTo }) =>
  (differenceInDays(new Date(dateTo), new Date(dateFrom)) + 1) * CELL_WIDTH; // +1 FOR FLAG

const getCorrectSvg = (flag, location, planned) => ({
  flagSvg: get(
    FLAGS_MAP.find(item => item.url === flag || item.urlDashed === flag),
    planned ? "urlDashed" : "url",
    null
  ),
  locationSvg: get(
    FLAG_LOCATIONS.find(item => item.id === location),
    planned ? "urlDashed" : "url",
    null
  ),
});

export function Event({ event, rightOffset, color, title, flag, onClick }) {
  const eventLength = getEventLength(event);
  const flagXCord = eventLength + rightOffset + "px";
  const { flagSvg, locationSvg } = getCorrectSvg(
    flag,
    event.location.type,
    event.planned
  );

  const eventStyle = {
    background: color,
    width: flag ? eventLength - CELL_WIDTH : eventLength,
  };
  const containerStyle = {
    width: eventLength + "px",
    right: rightOffset + "px",
  };

  return (
    <div
      className="event-container"
      id={"event-" + event.eventId}
      style={containerStyle}
      tabIndex="-1"
      onClick={onClick}
    >
      {flag && (
        <div
          style={{ right: flagXCord }}
          className="d-flex flex-column event-flag-container"
        >
          <img className="flag" src={flagSvg} alt="Event flag preview" />
          <span className="location-name">{event.location.name}</span>
          <img
            className="location-img"
            src={locationSvg}
            alt="Event flag preview"
          />
        </div>
      )}
      <div className="event" style={eventStyle}>
        {title}
      </div>

      {event.note && (
        <UncontrolledPopover
          trigger="hover"
          placement="bottom-start"
          target={"event-" + event.eventId}
        >
          <PopoverBody>
            {truncate(event.note, {
              length: 250,
            })}
          </PopoverBody>
        </UncontrolledPopover>
      )}
    </div>
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
