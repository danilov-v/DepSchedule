import React, { useState, useEffect, createRef } from "react";
import { Container, Row, Col } from "reactstrap";
import { addMonths } from "date-fns";
import { getUnits } from "helpers/api";
import { SECTIONS } from "stub-data/sections";
// import { UNITS } from "stub-data/units";
import { Title } from "components/title/title";
import { Calendar } from "components/calendar/calendar";
import { HighLevelSections } from "components/high-level-sections/high-level-sections";
import { UnitsGrid } from "components/units-grid/units-grid";

import { getDates } from "utils/date";

import "./timeline.scss";

export function Timeline() {
  const now = new Date();

  const [startDate, setStartDate] = useState(now);
  const [endDate, setEndDate] = useState(addMonths(now, 3));
  const [units, setUnits] = useState([]);
  const container = createRef();

  useEffect(() => {
    container.current.scrollLeft = container.current.scrollWidth;
    // if we add container to dependencies list each time when component
    // will recive new props or staete effect will be called,
    // hovewer we want to scroll to the header only first mount
    // and when calendar date is changed
  }, [startDate, endDate]); // eslint-disable-line react-hooks/exhaustive-deps

  useEffect(() => {
    const fetchUnits = async () => {
      const data = await getUnits();

      setUnits(data);
    };

    fetchUnits();
  }, []);

  const range = getDates(startDate, endDate);

  return (
    <Container className="timeline" fluid>
      <Title
        onChangeStartDate={setStartDate}
        onChangeEndDate={setEndDate}
        startDate={startDate}
        endDate={endDate}
      />
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
            <Calendar range={range} showMonth />
          </Col>
          <Col className="timeline-info">
            <UnitsGrid units={units} />
          </Col>
        </Row>
      </div>
    </Container>
  );
}
