import React from "react";
import classnames from "classnames";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export function NewUnit({ onAddUnit, hidden }) {
  return (
    <div
      className={classnames(
        { "d-none": hidden },
        { "d-flex": !hidden },
        "unit",
        "unit-new",
        "justify-content-center",
        "align-items-center",
        "text-center",
        "unit-last-generation"
      )}
      onClick={() => onAddUnit()}
    >
      <FontAwesomeIcon icon="plus" />
    </div>
  );
}
