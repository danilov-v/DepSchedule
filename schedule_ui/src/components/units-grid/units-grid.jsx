import React from "react";
import PropTypes from "prop-types";
import { Unit } from "./unit";

import "./units-grid.scss";

const renderUnit = ({ childUnit, title, unitId }) => {
  return childUnit && childUnit.length ? (
    <div key={unitId} className="d-flex flex-row-reverse">
      <Unit key={unitId} title={title} />
      <div>{childUnit.map(unit => renderUnit(unit))}</div>
    </div>
  ) : (
    <Unit key={unitId} title={title} lastGen />
  );
};

export function UnitsGrid({ units }) {
  return (
    <div className="units-grid">
      <div className="text-center units-grid-title">Unit Grid Title</div>
      {units.map(renderUnit)}
    </div>
  );
}

UnitsGrid.propTypes = {
  units: PropTypes.arrayOf(PropTypes.object).isRequired,
};
