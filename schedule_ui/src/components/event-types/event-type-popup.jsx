import React, { Fragment } from "react";
import PropTypes from "prop-types";
import {
  Button,
  Form,
  FormGroup,
  FormFeedback,
  Label,
  Input,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "reactstrap";
import { CirclePicker } from "react-color";
import {
  SUCCESS_EVENT_TYPE_NOTIFICATION_DATA,
  FAILED_EVENT_TYPE_NOTIFICATION_DATA,
} from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";
import { useForm } from "helpers/hooks/useForm";
import { EVENT_TYPE_COLORS } from "constants/calendar";

const validateEvenTypeForm = values => {
  let errors = {};

  if (!(values.description && values.description.length > 1))
    errors["description"] = "Необходимо выбрать название типа события";

  return errors;
};

export function EventTypePopup({
  type,
  isOpen,
  toggle,
  onEventTypeSubmit,
  defaultFormData,
}) {
  const { onChange, onSubmit, errors, errorsShown, values } = useForm(
    submitForm,
    defaultFormData,
    validateEvenTypeForm
  );

  const handleInputChange = event => {
    const { value, name } = event.target;
    onChange({ [name]: value });
  };

  const handleColorChange = color => onChange({ color: color.hex });

  async function submitForm() {
    try {
      await onEventTypeSubmit(values);
      toggle();

      NotificationManager.fire(SUCCESS_EVENT_TYPE_NOTIFICATION_DATA);
    } catch (e) {
      NotificationManager.fire(FAILED_EVENT_TYPE_NOTIFICATION_DATA);
    }
  }

  const { color, description } = values;
  return (
    <Fragment>
      <Modal isOpen={isOpen} toggle={toggle}>
        <Form className="p-3">
          <ModalHeader toggle={toggle}>
            {type === "create" ? "Создание" : "Редакитрование"} Типа события
          </ModalHeader>
          <ModalBody>
            <FormGroup>
              <Label for="description">Тип события</Label>
              <Input
                type="text"
                rows="6"
                name="description"
                id="note"
                value={description}
                onChange={handleInputChange}
                invalid={!!errors["description"] && errorsShown}
              />
              <FormFeedback>{errors["description"]}</FormFeedback>
            </FormGroup>
            <FormGroup>
              <Label for="color">Цвет события</Label>
              <CirclePicker
                color={color}
                onChangeComplete={handleColorChange}
                colors={EVENT_TYPE_COLORS}
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
              {type === "create" ? "Создать" : "Обновить"}
            </Button>
            <Button color="primary" onClick={toggle}>
              Закрыть
            </Button>
          </ModalFooter>
        </Form>
      </Modal>
    </Fragment>
  );
}

EventTypePopup.propTypes = {
  type: PropTypes.string,
  isOpen: PropTypes.bool,
  toggle: PropTypes.func,
  onEventTypeSubmit: PropTypes.func,
  defaultFormData: PropTypes.shape({
    color: PropTypes.string,
    description: PropTypes.string,
    typeId: PropTypes.number,
  }),
};
