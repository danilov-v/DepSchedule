import React from "react";
import { UnitPopup } from "./popups/unit-popup";
import { EventTypePopup } from "./popups/event-type-popup";
import { PrintBtn } from "./print-btn";

import "./admin-control.scss";

export function AdminControl(props) {
  return (
    <div className="admin-control mb-5">
      <UnitPopup {...props} />
      <EventTypePopup onEventTypesUpdate={props.onEventTypesUpdate} />
      <PrintBtn />
    </div>
  );
}
