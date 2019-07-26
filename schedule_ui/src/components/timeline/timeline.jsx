import React, { useState } from "react";
import { Container, Row, Col } from "reactstrap";
import { addMonths, addDays } from "date-fns";
import { Title } from "components/title/title";
import { Calendar } from "components/calendar/calendar";
import { HighLevelSections } from "components/high-level-sections/high-level-sections";

import { getDates } from "utils/date";

import "./timeline.scss";

export function Timeline() {
  const now = new Date();

  const [startDate, setStartDate] = useState(now);
  const [endDate, setEndDate] = useState(addMonths(now, 3));

  const sections = [
    {
      name: "A",
      startDate: new Date(2019, 7, 16),
      endDate: new Date(2019, 8, 16),
    },
    {
      name: "B",
      startDate: new Date(2019, 8, 17),
      endDate: new Date(2019, 8, 26),
    },
    {
      name: "C",
      startDate: new Date(2019, 8, 27),
      endDate: new Date(2019, 9, 10),
    },
    {
      name: "D",
      startDate: new Date(2019, 9, 12),
      endDate: new Date(2019, 9, 16),
    },
  ];

  // const sections = [
  //   {
  //     name: "A",
  //     startDate: new Date(2019, 6, 16),
  //     endDate: new Date(2019, 8, 16),
  //   },
  // ];

  console.log(sections);

  const range = getDates(startDate, endDate);

  return (
    <Container className="timeline">
      <Title
        onChangeStartDate={setStartDate}
        onChangeEndDate={setEndDate}
        startDate={startDate}
        endDate={endDate}
      />
      <div className="timeline-wrapper">
        <Row>
          <Col>
            <HighLevelSections
              startDate={startDate}
              range={range}
              sections={sections}
            />
          </Col>
        </Row>
        <Row className="flex-nowrap" noGutters>
          <Col className="timeline-left" xs="auto">
            <Calendar range={range} />
          </Col>
          <Col xs="auto" className="timeline-info">
            <div>test</div>
          </Col>
        </Row>
      </div>
    </Container>
  );
}
