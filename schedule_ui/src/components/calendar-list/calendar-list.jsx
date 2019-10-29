import React, { useState } from "react";
import PropTypes from "prop-types";
import { Container, Button, Table } from "reactstrap";
import { CalendarListModal } from "./calendar-list-modal";

import "./calendar-list.scss";

const CalendarListRow = ({ calendar, onCalendarSelect, onCalendarRemove }) => {
  const removeHandler = e => {
    e.stopPropagation();
    onCalendarRemove(calendar.calendarId);
  };

  return (
    <tr
      className="calendar-list-row"
      onClick={() => onCalendarSelect(calendar.calendarId)}
    >
      <td>{calendar.name}</td>
      <td>{calendar.isAstronomical ? "Да" : "Нет"}</td>
      <td>{calendar.shift}</td>
      <td>
        <Button color="danger" onClick={removeHandler}>
          Удалить
        </Button>
      </td>
    </tr>
  );
};

export function CalendarList({ calendars, onNewCalendarCreate, ...handlers }) {
  const [modal, setModal] = useState(false);
  const toggle = () => {
    setModal(!modal);
  };

  return (
    <Container className="calendar-list emblem-background py-4">
      <div className="select-form1">
        <h2 className="text-center mb-5">Выберите Календарь</h2>
        <Table hover>
          <thead>
            <tr>
              <th>Название</th>
              <th>Астрономическое время</th>
              <th>Сдвиг</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {calendars.map(calendar => (
              <CalendarListRow
                key={calendar.calendarId}
                calendar={calendar}
                {...handlers}
              />
            ))}
          </tbody>
        </Table>
        <div className="text-center1">
          <Button color="success" onClick={toggle}>
            Создать календарь
          </Button>
        </div>
        <CalendarListModal
          onToggle={toggle}
          modal={modal}
          onSubmit={onNewCalendarCreate}
        />
      </div>
    </Container>
  );
}

CalendarList.propTypes = {
  calendars: PropTypes.arrayOf(
    PropTypes.shape({
      calendarId: PropTypes.number,
      name: PropTypes.string,
      shift: PropTypes.number,
      isAstronomical: PropTypes.bool,
    })
  ),
  onCalendarSelect: PropTypes.func.isRequired,
  onNewCalendarCreate: PropTypes.func.isRequired,
  onCalendarRemove: PropTypes.func.isRequired,
};

CalendarList.defaultProps = {
  calendars: [],
};
