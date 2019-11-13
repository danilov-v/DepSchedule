import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";
import { useSelector, useDispatch } from "react-redux";
import { getUnitRemoveConfirmationOptions } from "constants/confirmations";
import { MANAGE_UNITS } from "constants/permissions";
import { getAuthData } from "redux/selectors/auth";
import { removeUnit } from "redux/actions/units";
import { checkPermission } from "utils/permissions";
import { useConfirmation } from "components/confirmation-service/confirmation-service";
import { openForm } from "redux/actions/forms";
import { UNIT_FORM } from "constants/forms";

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
  const { onAddUnit, onEditUnit, onRemoveUnit } = handlers;
  const addUnit = () => onAddUnit(unitId);
  const editUnit = () => onEditUnit(unitId);
  const removeUnit = () => onRemoveUnit(unitId);

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
        onAddUnit={addUnit}
        onEditUnit={editUnit}
        onRemoveUnit={removeUnit}
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
      <UnitLocation
        flag={flag}
        location={location}
        unitId={unitId}
        onEditUnit={editUnit}
      />
      <Unit
        title={title}
        unitId={unitId}
        flag={flag}
        lastGen
        lastOfGroup={isTopLevel}
        isManageAble={isManageAble}
        onAddUnit={addUnit}
        onEditUnit={editUnit}
        onRemoveUnit={removeUnit}
      />
    </div>
  );
};

export function UnitsGrid({ units, unitsTree }) {
  const dispatch = useDispatch();
  const confirm = useConfirmation();
  const { role } = useSelector(getAuthData);

  const onAddUnit = unitParentId => {
    dispatch(
      openForm({
        formName: UNIT_FORM,
        formData: unitParentId
          ? { parentId: unitParentId, title: "" }
          : undefined,
      })
    );
  };
  const onEditUnit = unitId => {
    const unit = units.find(unit => unit.unitId === unitId);

    dispatch(
      openForm({
        formName: UNIT_FORM,
        formData: unit,
        isEdit: true,
      })
    );
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
      <UnitPopup units={units} />
    </div>
  );
}

UnitsGrid.propTypes = {
  units: PropTypes.arrayOf(PropTypes.object).isRequired,
  unitsTree: PropTypes.arrayOf(PropTypes.object).isRequired,
};
