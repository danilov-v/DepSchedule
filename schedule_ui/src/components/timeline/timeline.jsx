import React, { useEffect, useRef } from "react";
import { Container, Row, Col } from "reactstrap";
import { useSelector, useDispatch } from "react-redux";
import { addDays, differenceInDays } from "date-fns";
import { getUnitsSelector } from "redux/selectors/units";
import { getActiveCalendarInfo } from "redux/selectors/calendars";
import { getUserDataSelector } from "redux/selectors/user";
import { fetchUnits } from "redux/actions/units";
import { Title } from "components/title/title";
import { Calendar } from "components/calendar/calendar";
import { EventCalendar } from "../event-calendar/event-calendar";
import { HighLevelSections } from "components/high-level-sections/high-level-sections";
import { UnitsGrid } from "components/units-grid/units-grid";
import { getDates } from "utils/date";
import { getLastGenUnits, getUnitsFromUnitsTree } from "./helpers";

import "./timeline.scss";

export function Timeline() {
  const dispatch = useDispatch();
  const container = useRef();
  const { operationalDate, isAstronomical } = useSelector(
    getActiveCalendarInfo
  );
  const { startDate, endDate } = useSelector(getUserDataSelector);
  const unitsTree = useSelector(getUnitsSelector);
  const units = getUnitsFromUnitsTree(unitsTree);
  const range = getDates(startDate, endDate);
  const operationalRange = getDates(
    operationalDate,
    addDays(operationalDate, differenceInDays(endDate, startDate))
  );

  useEffect(() => {
    dispatch(fetchUnits());
  }, [dispatch]);

  useEffect(() => {
    setTimeout(() => {
      if (container.current) {
        container.current.scrollLeft = container.current.scrollWidth;
      }
    }, 500);
  }, [dispatch, container, startDate]);

  return (
    <Container className="timeline d-flex flex-column" fluid>
      <Title text="График развертывания СУ" />
      <div ref={container} className="timeline-wrapper">
        <HighLevelSections range={range} />
        <Row className="flex-nowrap" noGutters>
          <Col className="timeline-left" xs="auto">
            <Calendar operationalRange={operationalRange} range={range} />
            <EventCalendar
              range={isAstronomical ? range : operationalRange}
              units={getLastGenUnits(unitsTree)}
            />
          </Col>
          <Col className="timeline-info">
            <UnitsGrid units={units} unitsTree={unitsTree} />
          </Col>
        </Row>
      </div>
    </Container>
  );
}
