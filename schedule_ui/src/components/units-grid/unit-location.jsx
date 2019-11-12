import React from "react";

export function UnitLocation({ location, flag }) {
  return (
    <div className="unit-location d-flex align-items-center justify-content-center">
      {flag && <img src={flag} alt="Unit flag preview" className="unit-flag" />}
      {location}
    </div>
  );
}
