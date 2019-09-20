import React, { useState } from "react";
import PropTypes from "prop-types";
import { isNull, get } from "lodash";
import classnames from "classnames";
import { useConfirmation } from "components/confirmation-service/confirmation-service";
import { getUnitRemoveConfirmationOptions } from "constants/confirmations";
import {
  SUCCESS_UNIT_NOTIFICATION_DATA_DELETE,
  FAILED_UNIT_NOTIFICATION_DATA,
} from "constants/notifications";
import { deleteUnit } from "helpers/api";
import { NotificationManager } from "helpers/notification-manager";

import { UnitPopup } from "./unit-popup";
import { Unit } from "./unit";
import { NewUnit } from "./new-unit";
import { MANAGE_UNITS } from "constants/permishions";
import { checkPermission } from "utils/permishions";
import { useAuth } from "components/auth-service/auth-service";

import "./units-grid.scss";

const renderUnit = (
  { childUnit, title, unitId },
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
    <Unit
      key={unitId}
      title={title}
      unitId={unitId}
      lastGen
      lastOfGroup={isTopLevel}
      isManageAble={isManageAble}
      {...handlers}
    />
  );
};

export function UnitsGrid({ units, unitsTree, onUnitsUpdate }) {
  const confirm = useConfirmation();
  const [unitData, setUnitData] = useState(null);
  const { authBody } = useAuth();

  const onCloseEditPopup = () => {
    setUnitData(null);
  };

  const onAddUnit = unitParentId => {
    if (unitParentId) {
      setUnitData({ parentId: unitParentId });
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
    confirm(getUnitRemoveConfirmationOptions(unit)).then(async () => {
      try {
        await deleteUnit(unitId);

        onUnitsUpdate();

        NotificationManager.fire(SUCCESS_UNIT_NOTIFICATION_DATA_DELETE);
      } catch {
        NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
      }
    });
  };

  const isManageAble = checkPermission(authBody.role, MANAGE_UNITS);

  return (
    <div className="units-grid">
      <div className="text-center units-grid-title">
        <div className="units-grid-title-text">
          ОВУ, соединения и воинские части <br /> (элементы СУ)
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
  onUnitsUpdate: PropTypes.func.isRequired,
};
