import React, { useState } from "react";
import PropTypes from "prop-types";
import { Route } from "react-router-dom";
import { addDays, differenceInDays, differenceInCalendarDays } from "date-fns";
import { isEmpty, cloneDeep, pick } from "lodash";
import {
  useUnitsTree,
  useEventTypes,
  usePeriods,
} from "helpers/hooks/apiEffects";
import { updateCalendar } from "helpers/api";
import { Container } from "reactstrap";
import { Timeline } from "components/timeline/timeline";
import { Header } from "components/header/header";
import { ConfirmationServiceProvider } from "components/confirmation-service/confirmation-service";
import { EventTypes } from "components/event-types/event-types";
import { FinishedEvents } from "components/finished-events/finished-events";
import { Periods } from "components/periods/periods";
import { getDayWithoutMinutes } from "utils/date";
import { FOUR_MONTH } from "constants/calendar";

import "./home.scss";

function getUnitsFromUnitsTree(root) {
  if (!root.length) return;

  const stack = cloneDeep(root).reverse();
  const array = [];

  while (stack.length !== 0) {
    const node = stack.pop();
    if (!isEmpty(node.childUnit)) {
      node.childUnit.forEach(unit => {
        stack.push(unit);
      });
    }
    array.push(pick(node, ["parentId", "title", "unitId", "flag", "planned"]));
  }

  return array;
}

export function Home({ activeCalendar, changeCalendar }) {
  const astronomicalDate = getDayWithoutMinutes(new Date());
  const [operationalDate, setOperationalDate] = useState(
    addDays(astronomicalDate, activeCalendar.shift)
  );
  const [startDate, setStartDate] = useState(astronomicalDate);
  const [endDate, setEndDate] = useState(addDays(astronomicalDate, FOUR_MONTH));
  const [eventTypes, fetchEventTypes] = useEventTypes();
  const [unitsTree, fetchUnitsTree] = useUnitsTree(startDate);
  const [periods, fetchPeriods] = usePeriods();

  const operationalRange = [
    operationalDate,
    addDays(operationalDate, differenceInDays(endDate, startDate)),
  ];

  const onOperationalDateChange = date => {
    setOperationalDate(date);

    const shift = differenceInCalendarDays(date, new Date());

    updateCalendar(activeCalendar.calendarId, shift);
  };

  const onUnitsUpdate = () => {
    fetchUnitsTree();
  };

  const onEventTypesUpdate = () => {
    fetchUnitsTree(); //if event deleted then all relative events will also destroed
    fetchEventTypes();
  };

  const onPeriodsUpdate = () => {
    fetchPeriods();
  };

  const setStartDateHander = date => {
    if (differenceInCalendarDays(endDate, date) > FOUR_MONTH) {
      setEndDate(addDays(date, FOUR_MONTH));
    }
    setStartDate(date);
  };

  const setEndDateHander = date => {
    if (differenceInCalendarDays(date, startDate) > FOUR_MONTH) {
      setStartDate(addDays(date, -FOUR_MONTH));
    }
    setEndDate(date);
  };

  return (
    <ConfirmationServiceProvider>
      <Container fluid>
        <Header
          startDate={startDate}
          endDate={endDate}
          operationalDate={operationalDate}
          onChangeOperationalDate={onOperationalDateChange}
          onChangeStartDate={setStartDateHander}
          onChangeEndDate={setEndDateHander}
          onCalendarChange={changeCalendar}
        />
        <main className="h-75 emblem-background">
          <Route
            exact
            path="/"
            render={() => (
              <Timeline
                operationalRange={operationalRange}
                startDate={startDate}
                endDate={endDate}
                eventTypes={eventTypes}
                unitsTree={unitsTree}
                periods={periods}
                units={getUnitsFromUnitsTree(unitsTree)}
                onUnitsUpdate={onUnitsUpdate}
              />
            )}
          />
          <Route
            path="/event_types"
            render={() => (
              <EventTypes
                eventTypes={eventTypes}
                onEventTypesUpdate={onEventTypesUpdate}
              />
            )}
          />
          <Route path="/finished_events" render={() => <FinishedEvents />} />
          <Route
            path="/periods"
            render={() => (
              <Periods periods={periods} onPeriodsUpdate={onPeriodsUpdate} />
            )}
          />
        </main>
      </Container>
    </ConfirmationServiceProvider>
  );
}

Home.propTypes = {
  activeCalendar: PropTypes.shape({
    calendarId: PropTypes.number,
    name: PropTypes.string,
    shift: PropTypes.number,
    isAstronomical: PropTypes.bool,
  }),
  changeCalendar: PropTypes.func.isRequired,
};

Home.defaultProps = {
  activeCalendar: null,
};
