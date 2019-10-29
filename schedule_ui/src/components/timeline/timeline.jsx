import React, { useEffect, useRef } from "react";
import PropTypes from "prop-types";
import { Container, Row, Col } from "reactstrap";
import { useSelector, useDispatch } from "react-redux";
import { addDays, differenceInDays } from "date-fns";
import {
  getStartDateSelector,
  getEndDateSelector,
  getUnitsSelector,
  getOperationalDateSelector,
} from "redux/selectors/scheduler";
import { fetchUnits } from "redux/actions/scheduler";
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
  const startDate = useSelector(getStartDateSelector);
  const endDate = useSelector(getEndDateSelector);
  const range = getDates(startDate, endDate);
  const operationalDate = useSelector(getOperationalDateSelector);
  const unitsTree = useSelector(getUnitsSelector);
  const units = getUnitsFromUnitsTree(unitsTree);

  const operationalRange = [
    operationalDate,
    addDays(operationalDate, differenceInDays(endDate, startDate)),
  ];

  useEffect(() => {
    dispatch(fetchUnits());

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
            <Calendar
              operationalRange={getDates(...operationalRange)}
              range={range}
            />
            <EventCalendar range={range} units={getLastGenUnits(unitsTree)} />
          </Col>
          <Col className="timeline-info">
            <UnitsGrid units={units} unitsTree={unitsTree} />
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
  units: [],
};
