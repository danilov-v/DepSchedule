import React from "react";
import PropTypes from "prop-types";
import { Col } from "reactstrap";
import classnames from "classnames";

import "./high-level-sections.scss";

export function HighLevelSection({ name, length, isEmpty }) {
  return (
    <Col
      xs="auto"
      style={{ width: 60 * length }}
      className={classnames(
        "section d-flex align-items-center justify-content-center",
        { empty: isEmpty }
      )}
    >
      {name}
    </Col>
  );
}

HighLevelSection.propTypes = {
  name: PropTypes.string,
  length: PropTypes.number,
  isEmpty: PropTypes.bool,
};

HighLevelSection.defaultProps = {
  name: null,
  length: 1,
  isEmpty: false,
};
