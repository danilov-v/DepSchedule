import React from "react";
import PropTypes from "prop-types";
import { Table, Button } from "reactstrap";
import { MANAGE_EVENT_TYPES } from "constants/permissions";
import { checkPermission } from "utils/permissions";

const ColorCircle = ({ color }) => (
  <span className="color-circle mr-1" style={{ background: color }} />
);

export function EventTypesList({
  eventTypes,
  onEventTypeClick,
  onEventTypeRemove,
  role,
}) {
  const isManageAble = checkPermission(role, MANAGE_EVENT_TYPES);

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
        <Button
          close
          onClick={onEventTypeRemove.bind(null, eventType.typeId)}
        />
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
  role: PropTypes.string,
};
