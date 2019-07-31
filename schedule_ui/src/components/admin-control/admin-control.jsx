import React from "react";
import { Button } from "reactstrap";
import { UnitPopup } from "./popups/unit-popup";

import "./admin-control.scss";

export function AdminControl(props) {
  return (
    <div className="admin-control mb-5">
      <UnitPopup {...props} />
      <Button color="primary mr-2">Создать Тип События</Button>
      <Button color="primary">Создать Событие</Button>
    </div>
  );
}
