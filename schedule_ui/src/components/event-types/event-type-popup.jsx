import React, { useEffect } from "react";
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
import { useForm } from "helpers/hooks/useForm";
import { EVENT_TYPE_COLORS } from "constants/calendar";

const validateEvenTypeForm = values => {
  let errors = {};

  if (!(values.description && values.description.length > 1))
    errors["description"] = "Необходимо выбрать название типа события";

  return errors;
};

export function EventTypePopup({
  isEdit,
  isOpen,
  toggle,
  onEventTypeSubmit,
  defaultFormData,
  error,
}) {
  const { onChange, onSubmit, errors, errorsShown, values } = useForm(
    submitForm,
    defaultFormData,
    validateEvenTypeForm
  );

  useEffect(() => {
    if (error) {
      //HANDLE FORM ERRORS HERE
    }
  }, [error]);

  const handleInputChange = event => {
    const { value, name } = event.target;
    onChange({ [name]: value });
  };

  const handleColorChange = color => onChange({ color: color.hex });

  function submitForm() {
    onEventTypeSubmit(values);
  }

  const { color, description } = values;
  return (
    <Modal isOpen={isOpen} toggle={toggle}>
      <Form className="p-3">
        <ModalHeader toggle={toggle}>
          {!isEdit ? "Создание" : "Редакитрование"} Типа события
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
            {!isEdit ? "Создать" : "Обновить"}
          </Button>
          <Button color="primary" onClick={toggle}>
            Закрыть
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
}

EventTypePopup.propTypes = {
  isEdit: PropTypes.bool,
  isOpen: PropTypes.bool,
  toggle: PropTypes.func,
  onEventTypeSubmit: PropTypes.func,
  error: PropTypes.object,
  defaultFormData: PropTypes.shape({
    color: PropTypes.string,
    description: PropTypes.string,
    typeId: PropTypes.number,
  }),
};
