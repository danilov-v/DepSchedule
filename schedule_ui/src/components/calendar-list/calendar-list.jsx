import React, { useState } from "react";
import PropTypes from "prop-types";
import {
  Container,
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Input,
} from "reactstrap";

import "./calendar-list.scss";

export function CalendarList({
  calendars,
  onCalendarSelect,
  onNewCalendarCreate,
}) {
  const [modal, setModal] = useState(false);
  const [newCalendarValue, setNewCalendarValue] = useState("");

  const toggle = () => {
    if (!modal) setNewCalendarValue("");
    setModal(!modal);
  };
  const createNewCalendar = () => {
    onNewCalendarCreate(newCalendarValue);

    toggle();
  };
  const handleChange = event => {
    setNewCalendarValue(event.target.value);
  };

  return (
    <Container className="calendar-list emblem-background">
      <div className="select-form">
        <h2 className="text-center mb-5">Выберите Календарь</h2>
        <div className="mb-3">
          {calendars.map(calendar => (
            <div
              className="calendar-list-item font-italic"
              key={calendar.calendarId}
              onClick={() => onCalendarSelect(calendar.calendarId)}
            >
              {calendar.name}
            </div>
          ))}
        </div>
        <Button color="success" onClick={toggle}>
          Создать новый календарь
        </Button>
        <Modal isOpen={modal} toggle={toggle}>
          <ModalHeader toggle={toggle}>Введите название календаря</ModalHeader>
          <ModalBody>
            <Input
              value={newCalendarValue}
              onChange={handleChange}
              placeholder="ВА РБ"
            />
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={createNewCalendar}>
              Создать
            </Button>{" "}
            <Button color="secondary" onClick={toggle}>
              Отменить
            </Button>
          </ModalFooter>
        </Modal>
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
};

CalendarList.defaultProps = {
  calendars: [],
};
