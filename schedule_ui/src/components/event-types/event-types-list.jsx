import React from "react";
import PropTypes from "prop-types";
import { ListGroup, ListGroupItem, Button } from "reactstrap";
import { useConfirmation } from "components/confirmation-service/confirmation-service";
import { EVENT_TYPE_CONFIRMATION_OPTIONS } from "constants/confirmations";

const ColorCircle = ({ color }) => (
  <span className="color-circle mr-1" style={{ background: color }} />
);

export function EventTypesList({
  eventTypes,
  onEventTypeClick,
  onEventTypeRemove,
}) {
  const confirm = useConfirmation();

  const onClick = eventType => ({ target }) => {
    target.tagName !== "SPAN" && onEventTypeClick(eventType);
  };

  const tryToRemove = typeId => {
    confirm(EVENT_TYPE_CONFIRMATION_OPTIONS).then(() =>
      onEventTypeRemove(typeId)
    );
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
          <Button close onClick={() => tryToRemove(eventType.typeId)} />
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
