import React from "react";
import PropTypes from "prop-types";
import { truncate, get } from "lodash";
import { UncontrolledTooltip } from "reactstrap";
import { CELL_WIDTH } from "constants/calendar";
import { differenceInDays } from "date-fns";
import { FLAGS_MAP, FLAG_LOCATIONS } from "constants/flags";

const getEventLength = ({ dateFrom, dateTo }) =>
  differenceInDays(new Date(dateTo), new Date(dateFrom)) * CELL_WIDTH;

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
    width: eventLength + "px",
    right: rightOffset + "px",
  };

  return (
    <div onClick={onClick}>
      <div
        id={"event-" + event.eventId}
        className="event"
        style={eventStyle}
        tabIndex="-1"
      >
        {title}
      </div>
      {flag && (
        <div
          style={{ right: flagXCord }}
          className="d-flex flex-column event-flag-container"
        >
          <img className="flag" src={flagSvg} alt="Event flag preview" />
          <img
            className="location"
            src={locationSvg}
            alt="Event flag preview"
          />
        </div>
      )}
      <UncontrolledTooltip
        placement="bottom-start"
        target={"event-" + event.eventId}
      >
        {truncate(event.note, {
          length: 250,
        })}
      </UncontrolledTooltip>
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
