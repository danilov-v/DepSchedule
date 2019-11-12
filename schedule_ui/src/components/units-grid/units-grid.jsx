import React, { useState } from "react";
import PropTypes from "prop-types";
import { isNull, get } from "lodash";
import classnames from "classnames";
import { useSelector, useDispatch } from "react-redux";
import { getUnitRemoveConfirmationOptions } from "constants/confirmations";
import { MANAGE_UNITS } from "constants/permissions";
import { getAuthData } from "redux/selectors/auth";
import { removeUnit } from "redux/actions/units";
import { checkPermission } from "utils/permissions";
import { useConfirmation } from "components/confirmation-service/confirmation-service";

import { UnitPopup } from "./unit-popup";
import { UnitLocation } from "./unit-location";
import { Unit } from "./unit";
import { NewUnit } from "./new-unit";

import "./units-grid.scss";

const renderUnit = (
  { childUnit, title, unitId, flag, location },
  isTopLevel = true,
  isManageAble,
  handlers
) => {
  return childUnit && childUnit.length ? (
    <div
      key={unitId}
      className={classnames("d-flex", "flex-row-reverse", {
        "unit-last-of-group": isTopLevel,
      })}
    >
      <Unit
        key={unitId}
        title={title}
        unitId={unitId}
        {...handlers}
        isManageAble={isManageAble}
      />
      <div>
        {childUnit.map(unit => renderUnit(unit, false, isManageAble, handlers))}
      </div>
    </div>
  ) : (
    <div
      className={classnames("d-flex", {
        "unit-last-of-group": isTopLevel,
      })}
      key={unitId}
    >
      <UnitLocation flag={flag} location={location} />
      <Unit
        title={title}
        unitId={unitId}
        flag={flag}
        lastGen
        lastOfGroup={isTopLevel}
        isManageAble={isManageAble}
        {...handlers}
      />
    </div>
  );
};

export function UnitsGrid({ units, unitsTree, onUnitsUpdate }) {
  const dispatch = useDispatch();
  const confirm = useConfirmation();
  const [unitData, setUnitData] = useState(null);
  const { role } = useSelector(getAuthData);

  const onCloseEditPopup = () => {
    setUnitData(null);
  };

  const onAddUnit = unitParentId => {
    if (unitParentId) {
      setUnitData({ parentId: unitParentId, title: "" });
    } else {
      setUnitData(undefined);
    }
  };
  const onEditUnit = editingUnit => {
    const unit = units.find(unit => unit.unitId === editingUnit.unitId);

    setUnitData(unit);
  };
  const onRemoveUnit = unitId => {
    const unit = units.find(unit => unit.unitId === unitId);
    confirm(getUnitRemoveConfirmationOptions(unit)).then(() =>
      dispatch(removeUnit({ unitId }))
    );
  };

  const isManageAble = checkPermission(role, MANAGE_UNITS);

  return (
    <div className="units-grid">
      <div className="text-center units-grid-title">
        <div className="d-flex units-grid-title-wrapper">
          <div className="units-grid-title-text units-grid-title-text-static">
            Положение ПУ
          </div>
          <div className="units-grid-title-text">Подсистемы управления</div>
        </div>
      </div>
      <div className="units-grid-body">
        {unitsTree.map(unit =>
          renderUnit(unit, true, isManageAble, {
            onAddUnit,
            onEditUnit,
            onRemoveUnit,
          })
        )}
        <NewUnit hidden={!isManageAble} onAddUnit={onAddUnit} />
      </div>
      {!isNull(unitData) && (
        <UnitPopup
          units={units}
          unit={unitData}
          onClose={onCloseEditPopup}
          onUnitsUpdate={onUnitsUpdate}
          isEdit={Boolean(get(unitData, "title"))}
        />
      )}
    </div>
  );
}

UnitsGrid.propTypes = {
  units: PropTypes.arrayOf(PropTypes.object).isRequired,
  unitsTree: PropTypes.arrayOf(PropTypes.object).isRequired,
};
