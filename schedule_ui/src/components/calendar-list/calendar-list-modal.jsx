import React, { useState } from "react";
import PropTypes from "prop-types";
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Input,
  FormGroup,
  Label,
} from "reactstrap";

export const CalendarListModal = ({ modal, onToggle, onSubmit }) => {
  const [newCalendarValue, setNewCalendarValue] = useState("");
  const [isAstronomical, setIsAstronomical] = useState(true);

  const handleChange = event => setNewCalendarValue(event.target.value);

  const handleCheckboxChange = event => setIsAstronomical(event.target.checked);

  const toggle = () => {
    setNewCalendarValue("");
    onToggle();
  };

  const submit = () => {
    if (newCalendarValue) {
      onSubmit({ name: newCalendarValue, shift: 0, isAstronomical });

      toggle();
    }
  };

  return (
    <Modal isOpen={modal} toggle={toggle}>
      <ModalHeader toggle={toggle}>Введите название календаря</ModalHeader>
      <ModalBody>
        <Input
          className="mb-2"
          value={newCalendarValue}
          onChange={handleChange}
          placeholder="ВА РБ"
        />
        <FormGroup check>
          <Label check>
            <Input
              checked={isAstronomical}
              onChange={handleCheckboxChange}
              type="checkbox"
              id="isAstronomical"
            />{" "}
            Астрономическое время
          </Label>
        </FormGroup>
      </ModalBody>
      <ModalFooter>
        <Button
          disabled={!Boolean(newCalendarValue)}
          color="primary"
          onClick={submit}
        >
          Создать
        </Button>{" "}
        <Button color="secondary" onClick={toggle}>
          Отменить
        </Button>
      </ModalFooter>
    </Modal>
  );
};

CalendarListModal.propTypes = {
  modal: PropTypes.bool,
  onToggle: PropTypes.func,
  onSubmit: PropTypes.func,
};
