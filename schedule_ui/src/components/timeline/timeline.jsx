import React, { useState, useEffect, createRef } from "react";
import { Container, Row, Col } from "reactstrap";
import { addMonths } from "date-fns";
import { useUnits, useUnitsTree, useEventTypes } from "helpers/effects";
import { SECTIONS } from "stub-data/sections";
import { AdminControl } from "components/admin-control/admin-control";
import { Title } from "components/title/title";
import { Calendar } from "components/calendar/calendar";
import { HighLevelSections } from "components/high-level-sections/high-level-sections";
import { UnitsGrid } from "components/units-grid/units-grid";
import { Notification } from "components/notification/notification";

import { getDates } from "utils/date";
import { getLastGenUnits } from "./helpers";

import "./timeline.scss";

export function Timeline() {
  const now = new Date();

  const [startDate, setStartDate] = useState(now);
  const [endDate, setEndDate] = useState(addMonths(now, 2));
  const [unitsTree, fetchUnitsTree] = useUnitsTree(startDate);
  const [eventTypes, fetchEventTypes] = useEventTypes();
  const [units, fetchUnits] = useUnits();
  const container = createRef();

  useEffect(() => {
    container.current.scrollLeft = container.current.scrollWidth;
    // if we add container to dependencies list each time when component
    // will recive new props or staete effect will be called,
    // hovewer we want to scroll to the header only first mount
  }, [unitsTree]); // eslint-disable-line react-hooks/exhaustive-deps

  const range = getDates(startDate, endDate);
  const onUnitsUpdate = () => {
    fetchUnits();
    fetchUnitsTree();
  };

  return (
    <Container className="timeline d-flex flex-column" fluid>
      <Title
        onChangeStartDate={setStartDate}
        onChangeEndDate={setEndDate}
        startDate={startDate}
        endDate={endDate}
      />
      <AdminControl
        units={units}
        onUnitsUpdate={onUnitsUpdate}
        onEventTypesUpdate={fetchEventTypes}
      />

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
