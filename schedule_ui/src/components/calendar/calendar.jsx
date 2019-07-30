import React, { Fragment } from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { CalendarCell } from "./calendar-cell";
import { UnitEventRow } from "../unit-event-row/unit-event-row";

import "./calendar.scss";

export function Calendar({ range, unitGroups, showMonth }) {
  return (
    <Fragment>
      <Row className="calendar" noGutters>
        {range.map(month => (
          <Col key={month.name}>
            {showMonth && (
              <Row className="calendar-title" noGutters>
                <Col>
                  <CalendarCell text={month.name} fluid />
                </Col>
              </Row>
            )}
            <Row noGutters>
              <Col className="d-flex">
                {month.days.map(day => (
                  <CalendarCell
                    key={day.getTime()}
                    text={showMonth ? day.getDate() : null}
                  />
                ))}
              </Col>
            </Row>
          </Col>
        ))}
      </Row>
      {unitGroups.map((group, i) => (
        <UnitEventRow key={i} range={range} unitGroup={group} />
      ))}
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
  showMonth: PropTypes.bool,
};

Calendar.defaultProps = {
  range: [],
  showMonth: false,
};
