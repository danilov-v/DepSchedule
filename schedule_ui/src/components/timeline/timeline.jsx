import React, { useState, useEffect, createRef } from "react";
import { Container, Row, Col } from "reactstrap";
import { addMonths } from "date-fns";
import { useUnitsTree, useUnits } from "helpers/effects";
import { SECTIONS } from "stub-data/sections";
import { AdminControl } from "components/admin-control/admin-control";
import { Title } from "components/title/title";
import { Calendar } from "components/calendar/calendar";
import { HighLevelSections } from "components/high-level-sections/high-level-sections";
import { UnitsGrid } from "components/units-grid/units-grid";

import { getDates } from "utils/date";
import { getLastGenUnits } from "./helpers";

import "./timeline.scss";

export function Timeline() {
  const now = new Date();

  const [startDate, setStartDate] = useState(now);
  const [endDate, setEndDate] = useState(addMonths(now, 3));
  const [unitsTree, fetchUnitsTree] = useUnitsTree();
  const [units, fetchUnits] = useUnits();
  const container = createRef();

  useEffect(() => {
    container.current.scrollLeft = container.current.scrollWidth;
    // if we add container to dependencies list each time when component
    // will recive new props or staete effect will be called,
    // hovewer we want to scroll to the header only first mount
    // and when calendar date is changed
  }, [startDate, endDate, units]); // eslint-disable-line react-hooks/exhaustive-deps

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
      <AdminControl units={units} onUnitsUpdate={onUnitsUpdate} />
      <div ref={container} className="timeline-wrapper">
        <Row>
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
              showMonth
            />
          </Col>
          <Col className="timeline-info">
            <UnitsGrid units={unitsTree} />
          </Col>
        </Row>
      </div>
    </Container>
  );
}
