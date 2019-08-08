import React, { useState, Fragment } from "react";
import { get, isBoolean } from "lodash";
import {
  Button,
  Form,
  FormGroup,
  Label,
  Input,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "reactstrap";
import { CirclePicker } from "react-color";
import { createEventType } from "helpers/api";
import {
  SUCCESS_EVENT_TYPE_NOTIFICATION_DATA,
  FAILED_EVENT_TYPE_NOTIFICATION_DATA,
} from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";

const DEFAULT_EVENT_TYPE = {
  eventColor: "#f44336",
  eventType: "",
};

const validateEventType = data =>
  Boolean(get(data, "eventType", "").length > 1);
const validateEventColor = data =>
  Boolean(get(data, "eventColor", "").length > 0);
const validate = data => validateEventType(data) && validateEventColor(data);

export function EventTypePopup({ onEventTypesUpdate }) {
  const [isValid, setValidState] = useState(null);
  const [isOpen, toggle] = useState(false);
  const [eventTypeData, setEventTypeData] = useState(DEFAULT_EVENT_TYPE);

  const resetPopup = () => {
    setEventTypeData(DEFAULT_EVENT_TYPE);
    setValidState(null);
  };

  const toggleModal = () => {
    toggle(!isOpen);

    if (isOpen) {
      resetPopup();
    }
  };

  const handleInputChange = event => {
    const value = event.target.value;
    const newEventTypeData = { ...eventTypeData, eventType: value };

    setValidState(validateEventType(newEventTypeData));
    setEventTypeData(newEventTypeData);
  };

  const handleColorChange = color =>
    setEventTypeData({ ...eventTypeData, eventColor: color.hex });

  const onSubmit = async event => {
    event.preventDefault();

    if (validate(eventTypeData)) {
      try {
        await createEventType({
          description: eventTypeData.eventType,
          color: eventTypeData.eventColor,
        });

        toggleModal();
        onEventTypesUpdate();
        NotificationManager.fire(SUCCESS_EVENT_TYPE_NOTIFICATION_DATA);
      } catch {
        setValidState(false);
        NotificationManager.fire(FAILED_EVENT_TYPE_NOTIFICATION_DATA);
      }
    } else {
      setValidState(false);
      NotificationManager.fire(FAILED_EVENT_TYPE_NOTIFICATION_DATA);
    }

    return false;
  };

  return (
    <Fragment>
      <Button onClick={toggleModal} color="primary" className="mr-3">
        Создать Тип События
      </Button>
      <Modal isOpen={isOpen} toggle={toggleModal}>
        <Form className="p-3">
          <ModalHeader toggle={toggleModal}>Создание Типа события</ModalHeader>
          <ModalBody>
            <FormGroup>
              <Label for="unitTitle">Тип события</Label>
              <Input
                name="eventType"
                id="eventType"
                placeholder="Мобилизация"
                valid={isValid}
                invalid={isBoolean(isValid) && !isValid}
                onChange={handleInputChange}
              />
            </FormGroup>
            <FormGroup>
              <Label for="eventColor">Цвет события</Label>
              <CirclePicker
                color={get(eventTypeData, "eventColor")}
                onChangeComplete={handleColorChange}
              />
            </FormGroup>
          </ModalBody>
          <ModalFooter>
            <Button
              type="submit"
              color="success"
              className="mr-3"
              onClick={onSubmit}
            >
              Создать
            </Button>
            <Button color="primary" onClick={toggleModal}>
              Закрыть
            </Button>
          </ModalFooter>
        </Form>
      </Modal>
    </Fragment>
  );
}
