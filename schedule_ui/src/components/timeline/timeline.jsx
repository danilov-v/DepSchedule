import React, { useEffect, useRef } from "react";
import PropTypes from "prop-types";
import { Container, Row, Col } from "reactstrap";
import { SECTIONS } from "stub-data/sections";
import { Title } from "components/title/title";
import { Calendar } from "components/calendar/calendar";
import { HighLevelSections } from "components/high-level-sections/high-level-sections";
import { UnitsGrid } from "components/units-grid/units-grid";
import { Notification } from "components/notification/notification";

import { getDates } from "utils/date";
import { getLastGenUnits } from "./helpers";

import "./timeline.scss";

export function Timeline({
  startDate,
  endDate,
  eventTypes,
  units,
  unitsTree,
  onUnitsUpdate,
}) {
  const container = useRef();

  useEffect(() => {
    container.current.scrollLeft = container.current.scrollWidth;
    // if we add container to dependencies list each time when component
    // will recive new props or staete effect will be called,
    // hovewer we want to scroll to the header only first mount
  }, [unitsTree]); // eslint-disable-line react-hooks/exhaustive-deps

  const range = getDates(startDate, endDate);

  return (
    <Container className="timeline d-flex flex-column" fluid>
      <Title text="График развертывания СУ" />
      <div ref={container} className="timeline-wrapper">
        <Row className="stick-to-top">
          <Col>
            <HighLevelSections
              startDate={startDate}
              range={range}
              sections={SECTIONS}
            />
          </Col>
        </Row>
        <Row className="flex-nowrap" noGutters>
          <Col className="timeline-left" xs="auto">
            <Calendar
              range={range}
              unitGroups={getLastGenUnits(unitsTree)}
              onUnitsUpdate={onUnitsUpdate}
              eventTypes={eventTypes}
              showMonth
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

      <Notification />
    </Container>
  );
}

Timeline.propTypes = {
  startDate: PropTypes.instanceOf(Date),
  endDate: PropTypes.instanceOf(Date),
  eventTypes: PropTypes.arrayOf(
    PropTypes.shape({
      color: PropTypes.string,
      description: PropTypes.string,
      typeId: PropTypes.number,
    })
  ),
  units: PropTypes.arrayOf(PropTypes.object),
  unitsTree: PropTypes.arrayOf(PropTypes.object).isRequired,
  onEventTypeRemove: PropTypes.func,
};

Timeline.defaultProps = {
  units: [],
};
