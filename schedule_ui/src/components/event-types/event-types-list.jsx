import React from "react";
import PropTypes from "prop-types";
import { ListGroup, ListGroupItem, Button } from "reactstrap";

const ColorCircle = ({ color }) => (
  <span className="color-circle mr-1" style={{ background: color }} />
);

export function EventTypesList({
  eventTypes,
  onEventTypeClick,
  onEventTypeRemove,
}) {
  const onClick = eventType => ({ target }) => {
    target.tagName !== "SPAN" && onEventTypeClick(eventType);
  };

  return (
    <ListGroup className="event-type-list">
      {eventTypes.map(eventType => (
        <ListGroupItem
          key={eventType.typeId}
          tag="a"
          className="event-type"
          action
          onClick={onClick(eventType)}
        >
          <ColorCircle color={eventType.color} />
          {eventType.description}
          <Button
            close
            onClick={onEventTypeRemove.bind(null, eventType.typeId)}
          />
        </ListGroupItem>
      ))}
    </ListGroup>
  );
}

EventTypesList.propTypes = {
  eventTypes: PropTypes.arrayOf(
    PropTypes.shape({
      color: PropTypes.string,
      description: PropTypes.string,
      typeId: PropTypes.number,
    })
  ),
  onEventTypeClick: PropTypes.func,
  onEventTypeRemove: PropTypes.func,
};
