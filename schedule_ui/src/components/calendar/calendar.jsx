import React from "react";
import classnames from "classnames";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { CalendarCell } from "./calendar-cell";
import { MONTH_TRANSLATIONS } from "../../constants/calendar";
import { CalendarMonthCellText } from "./calendar-month-cell-text";

import "./calendar.scss";

export function Calendar({ range, className }) {
  return (
    <Row className={classnames("calendar", className)} noGutters>
      {range.map(month => (
        <Col key={month.name}>
          <Row className="calendar-title" noGutters>
            <Col>
              <CalendarCell
                text={
                  <CalendarMonthCellText
                    text={MONTH_TRANSLATIONS[month.name]}
                    length={month.days.length}
                  />
                }
                fluid={month.days && month.days.length > 1}
              />
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
