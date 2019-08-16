import React from "react";
import { EventTypes } from "components/event-types/event-types";
import { UnitPopup } from "./popups/unit-popup";
import { PrintBtn } from "./print-btn";

import "./admin-control.scss";

export function AdminControl(props) {
  return (
    <div className="admin-control mb-5">
      <PrintBtn />
      <UnitPopup {...props} />
      <EventTypes {...props} />
    </div>
  );
}
