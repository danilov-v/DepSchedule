import React from "react";
import { Col } from "reactstrap";

import "./high-level-sections.scss";

export function HighLevelSection({ name, length }) {
  return (
    <Col xs="auto" style={{ width: 40 * length }} className="section">
      {name}
    </Col>
  );
}
