import React from "react";
import PropTypes from "prop-types";
import { Table, Button } from "reactstrap";
import { useConfirmation } from "components/confirmation-service/confirmation-service";
import { EVENT_TYPE_CONFIRMATION_OPTIONS } from "constants/confirmations";
import { FAILED_EVENT_TYPE_NOTIFICATION_DATA } from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";
import { MANAGE_EVENT_TYPES } from "constants/permishions";
import { checkPermission } from "utils/permishions";
import { useAuth } from "components/auth-service/auth-service";

const ColorCircle = ({ color }) => (
  <span className="color-circle mr-1" style={{ background: color }} />
);

export function EventTypesList({
  eventTypes,
  onEventTypeClick,
  onEventTypeRemove,
}) {
  const confirm = useConfirmation();
  const { getRole } = useAuth();

  const tryToRemove = typeId => {
    confirm(EVENT_TYPE_CONFIRMATION_OPTIONS).then(async () => {
      try {
        await onEventTypeRemove(typeId);
      } catch (e) {
        NotificationManager.fire(FAILED_EVENT_TYPE_NOTIFICATION_DATA);
      }
    });
  };
  const isManageAble = checkPermission(getRole(), MANAGE_EVENT_TYPES);

  const renderRow = (eventType, index) => (
    <tr key={eventType.typeId}>
      <th scope="row">{index}</th>
      <td>
        <ColorCircle color={eventType.color} />
      </td>
      <td>{eventType.description}</td>
      <td hidden={!isManageAble}>
        <Button
          onClick={onEventTypeClick.bind(null, eventType)}
          color="warning"
        >
          Изменить
        </Button>
        <Button close onClick={tryToRemove.bind(null, eventType.typeId)} />
      </td>
    </tr>
  );
  return (
    <div className="event-type-list">
      <Table hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Цвет</th>
            <th>Название</th>
            <th hidden={!isManageAble}>Управление</th>
          </tr>
        </thead>
        <tbody>{eventTypes.map(renderRow)}</tbody>
      </Table>
    </div>
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
