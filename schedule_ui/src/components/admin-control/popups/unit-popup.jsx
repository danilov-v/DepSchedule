import React, { useState, Fragment } from "react";
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
import { createUnit } from "helpers/api";
import {
  SUCCESS_UNIT_NOTIFICATION_DATA,
  FAILED_UNIT_NOTIFICATION_DATA,
} from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";

const DEFAULT_FORM_DATA = {
  unitParentId: 0,
  unitTitle: null,
};

export function UnitPopup({ units, onUnitsUpdate }) {
  const [isValid, setValidState] = useState(null);
  const [formData, setData] = useState(DEFAULT_FORM_DATA);
  const [isOpen, toggle] = useState(false);

  const resetPopup = () => {
    setData(DEFAULT_FORM_DATA);
    setValidState(null);
  };

  const toggleModal = () => {
    toggle(!isOpen);

    if (isOpen) {
      resetPopup();
    }
  };

  const validate = formData => {
    if (formData.unitTitle && formData.unitTitle.length >= 2) {
      return true;
    }

    return false;
  };

  const onInputChange = event => {
    const value = event.target.value;
    const newFormData = { ...formData, unitTitle: value };

    setValidState(validate(newFormData));
    setData(newFormData);
  };
  const onSelectChange = event => {
    const value = event.target.value;

    setData({ ...formData, unitParentId: value });
  };

  const onSubmit = async event => {
    event.preventDefault();

    if (validate(formData)) {
      const { unitParentId, unitTitle } = formData;
      const parentUnit = unitParentId
        ? units.find(unit => unit.unitId === +unitParentId)
        : null;

      try {
        await createUnit({
          title: unitTitle,
          parentId: parentUnit ? parentUnit.unitId : null,
          unitLevel: parentUnit ? parentUnit.unitLevel + 1 : 1,
        });

        onUnitsUpdate();
        toggleModal();

        NotificationManager.fire(SUCCESS_UNIT_NOTIFICATION_DATA);
      } catch {
        setValidState(false);
        NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
      }
    } else {
      setValidState(false);
      NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
    }

    return false;
  };

  return (
    <Fragment>
      <Button onClick={toggleModal} color="primary" className="mr-3">
        Создать Подразделение
      </Button>
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
              />
            </FormGroup>
            <FormGroup>
              <Label for="unitParent">Родительское Подразделение</Label>
              <Input
                type="select"
                name="unitParent"
                id="unitParent"
                onChange={onSelectChange}
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
