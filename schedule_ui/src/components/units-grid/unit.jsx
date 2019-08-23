import React from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import classnames from "classnames";

export function Unit({
  title,
  unitId,
  lastGen,
  lastOfGroup,
  onAddUnit,
  onEditUnit,
  onRemoveUnit,
}) {
  const addUnit = () => onAddUnit(unitId);
  const editUnit = () => onEditUnit({ unitId, title });
  const removeUnit = () => onRemoveUnit(unitId);
  const iconClass =
    "unit-action d-flex align-items-center justify-content-center h-100";
  return (
    <div
      className={classnames(
        "unit",
        "d-flex",
        "justify-content-center",
        "align-items-center",
        "text-center",
        { "unit-last-generation": lastGen },
        { "unit-last-of-group": lastOfGroup }
      )}
      data-unit-id={unitId}
    >
      {title}
      <Row className="unit-actions" noGutters>
        <Col xs={4} className={iconClass} onClick={addUnit}>
          <FontAwesomeIcon icon="plus" />
        </Col>
        <Col xs={4} className={iconClass} onClick={editUnit}>
          <FontAwesomeIcon icon="edit" />
        </Col>
        <Col xs={4} className={iconClass} onClick={removeUnit}>
          <FontAwesomeIcon icon="trash-alt" />
        </Col>
      </Row>
    </div>
  );
}

Unit.propTypes = {
  title: PropTypes.string.isRequired,
  unitId: PropTypes.number.isRequired,
  lastGen: PropTypes.bool,
};
