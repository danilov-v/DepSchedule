import React, { useState } from "react";
import { Switch, Route } from "react-router-dom";
import { addMonths } from "date-fns";
import {
  useUnits,
  useUnitsTree,
  useEventTypes,
} from "helpers/hooks/apiEffects";
import { Container } from "reactstrap";
import { Timeline } from "components/timeline/timeline";
import { Header } from "components/header/header";
import { ConfirmationServiceProvider } from "components/confirmation-service/confirmation-service";
import { EventTypes } from "components/event-types/event-types";
import { getDayWithoutMinutes } from "utils/date";

import "./home.scss";

export function Home() {
  const now = getDayWithoutMinutes(new Date());

  const [startDate, setStartDate] = useState(now);
  const [endDate, setEndDate] = useState(addMonths(now, 2));
  const [eventTypes, fetchEventTypes] = useEventTypes();
  const [unitsTree, fetchUnitsTree] = useUnitsTree(startDate);
  const [units, fetchUnits] = useUnits();

  const onUnitsUpdate = () => {
    fetchUnits();
    fetchUnitsTree();
  };

  const onEventTypesUpdate = () => {
    fetchUnitsTree(); //if event deleted then all relative events will also destroed
    fetchEventTypes();
  };

  return (
    <ConfirmationServiceProvider>
      <Container fluid>
        <Header
          startDate={startDate}
          endDate={endDate}
          onChangeStartDate={setStartDate}
          onChangeEndDate={setEndDate}
        />
        <Switch>
          <Route
            exact
            path="/"
            render={() => (
              <Timeline
                startDate={startDate}
                endDate={endDate}
                eventTypes={eventTypes}
                unitsTree={unitsTree}
                units={units}
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
        </Switch>
      </Container>
    </ConfirmationServiceProvider>
  );
}
