import React, { useState } from "react";
import { Button, Form, FormGroup, Label, Input } from "reactstrap";
import Popup from "reactjs-popup";
import { isBoolean } from "lodash";
import { createUnit } from "helpers/api";

export function UnitPopup({ units, onUnitsUpdate }) {
  const [isValid, setValidState] = useState(null);

  console.log(units);

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
      } catch {}
    } else {
      setValidState(false);
    }

    return false;
  };

  return (
    <Popup
      trigger={
        <Button color="primary" className="mr-3">
          Создать Подразделение
        </Button>
      }
      modal
    >
      {close => (
        <Form
          className="p-3"
          onSubmit={event => {
            onSubmit(event);
            close();
          }}
        >
          <h3 className="text-center">Создание Подразделения</h3>
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
          <Button type="submit" color="success" className="mr-3">
            Создать
          </Button>
          <Button
            color="primary"
            onClick={() => {
              close();
            }}
          >
            Закрыть
          </Button>
        </Form>
      )}
    </Popup>
  );
}
