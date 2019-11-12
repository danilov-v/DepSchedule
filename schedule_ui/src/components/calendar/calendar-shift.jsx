import React, { Fragment } from "react";
import { isWithinInterval, differenceInDays } from "date-fns";
import { Row, Col } from "reactstrap";
import { useSelector } from "react-redux";
import { getFormattedPeriodsSelector } from "redux/selectors/periods";
import { CalendarCell } from "./calendar-cell";

const getPeriod = (day, periods) =>
  periods.find(period =>
    isWithinInterval(day, { start: period.startDate, end: period.endDate })
  );

const getShift = (day, period) => differenceInDays(day, period.startDate);

export function CalendarShift({ range }) {
  const periods = useSelector(getFormattedPeriodsSelector);

  return (
    <Row className="calendar calendar-shift flex-nowrap" noGutters>
      {range.map(month => (
        <Fragment key={month.name}>
          {month.days.map(day => {
            const period = getPeriod(day, periods);
            const shift = period && getShift(day, period).toString();

            return (
              <Col key={day.getTime()}>
                <CalendarCell
                  text={shift ? `M${shift}` : ""}
                  className={
                    shift ? "calendar-cell-shift" : "calendar-cell-empty"
                  }
                />
              </Col>
            );
          })}
        </Fragment>
      ))}
    </Row>
  );
}
