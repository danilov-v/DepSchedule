import React, { useState } from "react";
import PropTypes from "prop-types";
import { get } from "lodash";
import { Unit } from "./unit";
import classnames from "classnames";
import { UnitPopup } from "components/admin-control/popups/unit-popup";

import "./units-grid.scss";

const renderUnit = ({ childUnit, title, unitId }, isTopLevel = true) => {
  return childUnit && childUnit.length ? (
    <div
      key={unitId}
      className={classnames("d-flex", "flex-row-reverse", {
        "unit-last-of-group": isTopLevel,
      })}
    >
      <Unit key={unitId} title={title} unitId={unitId} />
      <div>{childUnit.map(unit => renderUnit(unit, false))}</div>
    </div>
  ) : (
    <Unit
      key={unitId}
      title={title}
      unitId={unitId}
      lastGen
      lastOfGroup={isTopLevel}
    />
  );
};

export function UnitsGrid({ units, unitsTree, onUnitsUpdate }) {
  const [editUnitData, setEditUnit] = useState(null);
  const clickHandler = event => {
    const unitId = get(event, "target.dataset.unitId");

    if (!unitId) return false;

    const editUnit = units.find(unit => unit.unitId === +unitId);

    setEditUnit(editUnit);
  };
  const onCloseEditPopup = () => {
    setEditUnit(null);
  };

  return (
    <div className="units-grid">
      <div className="text-center units-grid-title">Список Подразделений</div>
      <div className="unit-grid-body" onClick={clickHandler}>
        {unitsTree.map(unit => renderUnit(unit))}
      </div>
      {editUnitData && (
        <UnitPopup
          units={units}
          unit={editUnitData}
          onClose={onCloseEditPopup}
          onUnitsUpdate={onUnitsUpdate}
          isEdit
        />
      )}
    </div>
  );
}

UnitsGrid.propTypes = {
  units: PropTypes.arrayOf(PropTypes.object).isRequired,
  unitsTree: PropTypes.arrayOf(PropTypes.object).isRequired,
  onUnitsUpdate: PropTypes.func.isRequired,
};
