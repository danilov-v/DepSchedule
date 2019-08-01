import React, { useState, Fragment } from "react";
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
import { isBoolean } from "lodash";
import { createUnit, updateUnit, deleteUnit } from "helpers/api";
import {
  SUCCESS_UNIT_NOTIFICATION_DATA,
  SUCCESS_UNIT_NOTIFICATION_DATA_EDIT,
  SUCCESS_UNIT_NOTIFICATION_DATA_DELETE,
  FAILED_UNIT_NOTIFICATION_DATA,
} from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";

const DEFAULT_FORM_DATA = {
  title: "",
  parentId: 0,
};

export function UnitPopup({ units, unit, isEdit, onUnitsUpdate, onClose }) {
  const [isValid, setValidState] = useState(null);
  const [formData, setData] = useState(unit);
  const [isOpen, toggle] = useState(isEdit);
  const resetPopup = () => {
    setData(DEFAULT_FORM_DATA);
    setValidState(null);
  };

  const toggleModal = () => {
    toggle(!isOpen);

    if (isOpen) {
      resetPopup();

      onClose();
    }
  };

  const validate = formData => {
    if (formData.title && formData.title.length >= 2) {
      return true;
    }

    return false;
  };

  const onInputChange = event => {
    const value = event.target.value;
    const newFormData = { ...formData, title: value };

    setValidState(validate(newFormData));
    setData(newFormData);
  };
  const onSelectChange = event => {
    const value = event.target.value;

    setData({ ...formData, parentId: +value });
  };

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

  const onRemoveUnit = async () => {
    try {
      await deleteUnit(formData.unitId);

      onUnitsUpdate();
      toggleModal();

      NotificationManager.fire(SUCCESS_UNIT_NOTIFICATION_DATA_DELETE);
    } catch {
      NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
    }
  };

  return (
    <Fragment>
      {!isEdit && (
        <Button onClick={toggleModal} color="primary" className="mr-3">
          Создать Подразделение
        </Button>
      )}
      <Modal isOpen={isOpen} toggle={toggleModal}>
        <Form className="p-3" onSubmit={onSubmit}>
          <ModalHeader toggle={toggleModal}>Создание Подразделения</ModalHeader>
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
            {isEdit && (
              <Button color="danger" onClick={onRemoveUnit} className="mr-3">
                Удалить
              </Button>
            )}
            <Button color="primary" onClick={toggleModal}>
              Закрыть
            </Button>
          </ModalFooter>
        </Form>
      </Modal>
    </Fragment>
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
