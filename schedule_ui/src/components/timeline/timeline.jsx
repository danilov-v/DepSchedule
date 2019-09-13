import React, { useEffect, useRef } from "react";
import PropTypes from "prop-types";
import { Container, Row, Col } from "reactstrap";
import { Title } from "components/title/title";
import { Calendar } from "components/calendar/calendar";
import { EventCalendar } from "../event-calendar/event-calendar";
import { HighLevelSections } from "components/high-level-sections/high-level-sections";
import { UnitsGrid } from "components/units-grid/units-grid";
import { getDates } from "utils/date";
import { getLastGenUnits, formatPeriods } from "./helpers";

import "./timeline.scss";

export function Timeline({
  operationalRange,
  startDate,
  endDate,
  eventTypes,
  unitsTree,
  units,
  periods,
  onUnitsUpdate,
}) {
  const container = useRef();

  useEffect(() => {
    setTimeout(() => {
      if (container.current) {
        container.current.scrollLeft = container.current.scrollWidth;
      }
    }, 500);
  }, [container, startDate]);

  const range = getDates(startDate, endDate);

  return (
    <Container className="timeline d-flex flex-column" fluid>
      <Title text="График развертывания СУ" />
      <div ref={container} className="timeline-wrapper">
        <HighLevelSections
          startDate={startDate}
          range={range}
          periods={formatPeriods(periods)}
        />
        <Row className="flex-nowrap" noGutters>
          <Col className="timeline-left" xs="auto">
            <Calendar
              operationalRange={getDates(...operationalRange)}
              range={range}
            />
            <EventCalendar
              range={range}
              units={getLastGenUnits(unitsTree)}
              onUnitsUpdate={onUnitsUpdate}
              eventTypes={eventTypes}
            />
          </Col>
          <Col className="timeline-info">
            <UnitsGrid
              units={units}
              unitsTree={unitsTree}
              onUnitsUpdate={onUnitsUpdate}
            />
          </Col>
        </Row>
      </div>
    </Container>
  );
}

Timeline.propTypes = {
  operationalDate: PropTypes.instanceOf(Date),
  operationalRange: PropTypes.arrayOf(PropTypes.instanceOf(Date)),
  startDate: PropTypes.instanceOf(Date),
  endDate: PropTypes.instanceOf(Date),
  eventTypes: PropTypes.arrayOf(
    PropTypes.shape({
      color: PropTypes.string,
      description: PropTypes.string,
      typeId: PropTypes.number,
    })
  ),
  periods: PropTypes.arrayOf(
    PropTypes.shape({
      periodId: PropTypes.number,
      name: PropTypes.string,
      startDate: PropTypes.string,
      endDate: PropTypes.string,
    })
  ),
  units: PropTypes.arrayOf(PropTypes.object),
  unitsTree: PropTypes.arrayOf(PropTypes.object).isRequired,
  onEventTypeRemove: PropTypes.func,
};

Timeline.defaultProps = {
  unitsTree: [],
};
