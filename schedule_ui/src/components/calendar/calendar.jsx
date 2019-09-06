import React, { Fragment } from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { CalendarCell } from "./calendar-cell";
import { EventCalendar } from "../event-calendar/event-calendar";
import { MONTH_TRANSLATIONS } from "../../constants/calendar";

import "./calendar.scss";

export function Calendar({
  range,
  showMonth,
  unitGroups,
  eventTypes,
  onUnitsUpdate,
  operationalDate,
}) {
  return (
    <Fragment>
      <Row className="calendar" noGutters>
        {range.map(month => (
          <Col key={month.name}>
            {showMonth && (
              <Row className="calendar-title" noGutters>
                <Col>
                  <CalendarCell
                    text={MONTH_TRANSLATIONS[month.name]}
                    fluid={month.days && month.days.length > 1}
                  />
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
      <EventCalendar
        range={range}
        unitGroups={unitGroups}
        onUnitsUpdate={onUnitsUpdate}
        eventTypes={eventTypes}
        operationalDate={operationalDate}
      />
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
  unitGroups: PropTypes.arrayOf(PropTypes.arrayOf(PropTypes.object)), //will change it to PropTypes.shape after BE fixes
  eventTypes: PropTypes.arrayOf(
    PropTypes.shape({
      color: PropTypes.string,
      description: PropTypes.string,
      typeId: PropTypes.number,
    })
  ),
  onUnitsUpdate: PropTypes.func.isRequired,
  operationalDate: PropTypes.instanceOf(Date),
};

Calendar.defaultProps = {
  range: [],
  showMonth: false,
  unitGroups: [],
  eventTypes: [],
};
