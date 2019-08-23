import React, { useState, useEffect, useRef } from "react";
import PropTypes from "prop-types";
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
import { get, isBoolean } from "lodash";
import { createUnit, updateUnit } from "helpers/api";
import {
  SUCCESS_UNIT_NOTIFICATION_DATA,
  SUCCESS_UNIT_NOTIFICATION_DATA_EDIT,
  FAILED_UNIT_NOTIFICATION_DATA,
} from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";

const DEFAULT_FORM_DATA = {
  title: "",
  parentId: 0,
};

const validate = formData =>
  Boolean(formData.title && formData.title.length >= 2);

export function UnitPopup({ units, unit, isEdit, onUnitsUpdate, onClose }) {
  const [isValid, setValidState] = useState(null);
  const [formData, setData] = useState(unit);
  const titleInputEl = useRef(null);

  const toggleModal = () => {
    onClose();
  };

  const onInputChange = event => {
    const newFormData = { ...formData, title: get(event, "target.value") };

    setValidState(validate(newFormData));
    setData(newFormData);
  };

  const onSelectChange = event =>
    setData({ ...formData, parentId: +get(event, "target.value") });

  const onSubmit = async event => {
    event.preventDefault();

    if (validate(formData)) {
      try {
        if (isEdit) {
          await updateUnit(formData);
        } else {
          await createUnit(formData);
        }

        onUnitsUpdate();
        toggleModal();

        NotificationManager.fire(
          isEdit
            ? SUCCESS_UNIT_NOTIFICATION_DATA_EDIT
            : SUCCESS_UNIT_NOTIFICATION_DATA
        );
      } catch (e) {
        setValidState(false);
        NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
      }
    } else {
      setValidState(false);
      NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
    }

    return false;
  };

  const onEnter = () => {
    setTimeout(() => {
      if (titleInputEl.current) titleInputEl.current.focus();
    });
  };

  return (
    <Modal isOpen={true} toggle={toggleModal} onEnter={onEnter}>
      <Form className="p-3" onSubmit={onSubmit}>
        <ModalHeader toggle={toggleModal}>
          {isEdit ? "Редактирование" : "Создание"} Подразделения
        </ModalHeader>
        <ModalBody>
          <FormGroup>
            <Label for="unitTitle">Название Подразделения</Label>
            <Input
              name="unitTitle"
              id="unitTitle"
              placeholder="СУ"
              valid={isValid}
              invalid={isBoolean(isValid) && !isValid}
              onChange={onInputChange}
              value={formData.title}
              innerRef={titleInputEl}
            />
          </FormGroup>
          <FormGroup>
            <Label for="unitParent">Родительское Подразделение</Label>
            <Input
              type="select"
              name="unitParent"
              id="unitParent"
              onChange={onSelectChange}
              defaultValue={formData.parentId}
            >
              <option value={0}>Новое Подразделение</option>
              {units.map((unit, i) => (
                <option key={unit.title + i} value={unit.unitId}>
                  {unit.title}
                </option>
              ))}
            </Input>
          </FormGroup>
        </ModalBody>
        <ModalFooter>
          <Button type="submit" color="success" className="mr-3">
            {isEdit ? "Обновить" : "Создать"}
          </Button>
          <Button color="primary" onClick={toggleModal}>
            Закрыть
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
}

UnitPopup.propTypes = {
  unit: PropTypes.shape({
    unitId: PropTypes.number,
    parentId: PropTypes.number,
    title: PropTypes.string,
  }),
  onClose: PropTypes.func,
};

UnitPopup.defaultProps = {
  unit: DEFAULT_FORM_DATA,
  onClose: () => {},
};
