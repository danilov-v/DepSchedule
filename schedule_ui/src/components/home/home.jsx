import React, { useState } from "react";
import { Route } from "react-router-dom";
import { addDays, differenceInDays } from "date-fns";
import { isEmpty, cloneDeep } from "lodash";
import {
  useUnitsTree,
  useEventTypes,
  usePeriods,
} from "helpers/hooks/apiEffects";
import { Container } from "reactstrap";
import { Timeline } from "components/timeline/timeline";
import { Header } from "components/header/header";
import { ConfirmationServiceProvider } from "components/confirmation-service/confirmation-service";
import { EventTypes } from "components/event-types/event-types";
import { Periods } from "components/periods/periods";
import { getDayWithoutMinutes } from "utils/date";

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
    array.push({
      parentId: node.parentId,
      title: node.title,
      unitId: node.unitId,
    });
  }

  return array;
}

export function Home() {
  const astronomicalDate = getDayWithoutMinutes(new Date());
  const [operationalDate, setOperationalDate] = useState(astronomicalDate);
  const [startDate, setStartDate] = useState(astronomicalDate);
  const [endDate, setEndDate] = useState(addDays(astronomicalDate, 60));
  const [eventTypes, fetchEventTypes] = useEventTypes();
  const [unitsTree, fetchUnitsTree] = useUnitsTree(startDate);
  const [periods, fetchPeriods] = usePeriods();
  const operationalRange = [
    operationalDate,
    addDays(operationalDate, differenceInDays(endDate, startDate)),
  ];

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

  return (
    <ConfirmationServiceProvider>
      <Container fluid>
        <Header
          startDate={startDate}
          endDate={endDate}
          operationalDate={operationalDate}
          onChangeOperationalDate={setOperationalDate}
          onChangeStartDate={setStartDate}
          onChangeEndDate={setEndDate}
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
