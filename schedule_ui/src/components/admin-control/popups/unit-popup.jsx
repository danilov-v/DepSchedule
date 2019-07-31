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

export function UnitPopup({ units, onUnitsUpdate }) {
  const [isValid, setValidState] = useState(null);
  const [isOpen, toggle] = useState(false);
  const toggleModal = () => {
    toggle(!isOpen);
  };

  const onSubmit = async event => {
    event.preventDefault();

    const data = new FormData(event.target);
    const unitTitle = data.get("unitTitle");
    const unitParentId = data.get("unitParent");
    const parentUnit = unitParentId
      ? units.find(unit => unit.unitId === +unitParentId)
      : null;

    if (unitTitle) {
      setValidState(true);

      try {
        await createUnit({
          title: unitTitle,
          parentId: parentUnit ? parentUnit.unitId : null,
          unitLevel: parentUnit ? parentUnit.unitLevel + 1 : 1,
        });

        onUnitsUpdate();
        toggleModal();
      } catch {}
    } else {
      setValidState(false);
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
              />
            </FormGroup>
            <FormGroup>
              <Label for="unitParent">Родительское Подразделение</Label>
              <Input type="select" name="unitParent" id="unitParent">
                <option value={undefined}>Новое Подразделение</option>
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
