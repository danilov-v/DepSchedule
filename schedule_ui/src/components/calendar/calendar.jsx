import React, { Fragment } from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { CalendarCell } from "./calendar-cell";
import { MONTH_TRANSLATIONS } from "../../constants/calendar";

import "./calendar.scss";

export function Calendar({ range, operationalRange }) {
  return (
    <Fragment>
      <Row className="calendar calendar-operational" noGutters>
        {operationalRange.map(month => (
          <Col key={month.name}>
            <Row className="calendar-title" noGutters>
              <Col>
                <CalendarCell
                  text={MONTH_TRANSLATIONS[month.name]}
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
      <Row className="calendar" noGutters>
        {range.map(month => (
          <Col key={month.name}>
            <Row className="calendar-title" noGutters>
              <Col>
                <CalendarCell
                  text={MONTH_TRANSLATIONS[month.name]}
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
    </Fragment>
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
