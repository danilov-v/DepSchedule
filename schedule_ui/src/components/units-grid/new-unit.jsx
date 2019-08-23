import React from "react";
import classnames from "classnames";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export function NewUnit({ onAddUnit }) {
  return (
    <div
      className={classnames(
        "unit",
        "d-flex",
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
