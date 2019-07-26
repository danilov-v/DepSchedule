import React from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { CalendarCell } from "./calendar-cell";

import "./calendar.scss";

export function Calendar({ range }) {
  return (
    <Row className="calendar" noGutters>
      {range.map(month => (
        <Col key={month.name}>
          <Row className="calendar-title" noGutters>
            <Col>
              <CalendarCell text={month.name} fluid />
            </Col>
          </Row>
          <Row noGutters>
            <Col className="d-flex">
              {month.days.map(day => (
                <CalendarCell key={day.getTime()} text={day.getDate()} />
              ))}
            </Col>
          </Row>
        </Col>
      ))}
    </Row>
  );
}

Calendar.propTypes = {
  range: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string,
      days: PropTypes.arrayOf(PropTypes.object),
    })
  ),
};

Calendar.defaultProps = {
  range: [],
};
