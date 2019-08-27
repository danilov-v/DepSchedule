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

import "./units-grid.scss";

const renderUnit = (
  { childUnit, title, unitId },
  isTopLevel = true,
  handlers
) => {
  return childUnit && childUnit.length ? (
    <div
      key={unitId}
      className={classnames("d-flex", "flex-row-reverse", {
        "unit-last-of-group": isTopLevel,
      })}
    >
      <Unit key={unitId} title={title} unitId={unitId} {...handlers} />
      <div>{childUnit.map(unit => renderUnit(unit, false, handlers))}</div>
    </div>
  ) : (
    <Unit
      key={unitId}
      title={title}
      unitId={unitId}
      lastGen
      lastOfGroup={isTopLevel}
      {...handlers}
    />
  );
};

export function UnitsGrid({ units, unitsTree, onUnitsUpdate }) {
  const confirm = useConfirmation();
  const [unitData, setUnitData] = useState(null);
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

  return (
    <div className="units-grid">
      <div className="text-center units-grid-title">Список Подразделений</div>
      <div className="unit-grid-body">
        {unitsTree.map(unit =>
          renderUnit(unit, true, { onAddUnit, onEditUnit, onRemoveUnit })
        )}
        <NewUnit onAddUnit={onAddUnit} />
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
