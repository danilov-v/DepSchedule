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
  isManageAble,
  onAddUnit,
  onEditUnit,
  onRemoveUnit,
}) {
  const iconClass =
    "unit-action d-flex align-items-center justify-content-center h-100";
  const manageStyle = isManageAble ? {} : { display: "none" };

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
      <div>{title}</div>

      <Row style={manageStyle} className="unit-actions" noGutters>
        <Col xs={4} className={iconClass} onClick={onAddUnit}>
          <FontAwesomeIcon icon="plus" />
        </Col>
        <Col xs={4} className={iconClass} onClick={onEditUnit}>
          <FontAwesomeIcon icon="edit" />
        </Col>
        <Col xs={4} className={iconClass} onClick={onRemoveUnit}>
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
