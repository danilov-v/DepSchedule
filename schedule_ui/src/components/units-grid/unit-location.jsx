import React from "react";

export function UnitLocation({ location, flag, onEditUnit }) {
  return (
    <div
      className="unit-location d-flex align-items-center justify-content-center"
      onClick={onEditUnit}
    >
      {flag && <img src={flag} alt="Unit flag preview" className="unit-flag" />}
      {location}
    </div>
  );
}
