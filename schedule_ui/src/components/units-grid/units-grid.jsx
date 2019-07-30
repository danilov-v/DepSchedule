import React from "react";
import PropTypes from "prop-types";
import { Unit } from "./unit";
import classnames from "classnames";

import "./units-grid.scss";

const renderUnit = ({ childUnit, title, unitId }, isTopLevel = true) => {
  return childUnit && childUnit.length ? (
    <div
      key={unitId}
      className={classnames("d-flex", "flex-row-reverse", {
        "unit-last-of-group": isTopLevel,
      })}
    >
      <Unit key={unitId} title={title} />
      <div>{childUnit.map(unit => renderUnit(unit, false))}</div>
    </div>
  ) : (
    <Unit key={unitId} title={title} lastGen lastOfGroup={isTopLevel} />
  );
};

export function UnitsGrid({ units }) {
  return (
    <div className="units-grid">
      <div className="text-center units-grid-title">Unit Grid Title</div>
      {units.map(unit => renderUnit(unit))}
    </div>
  );
}

UnitsGrid.propTypes = {
  units: PropTypes.arrayOf(PropTypes.object).isRequired,
};
