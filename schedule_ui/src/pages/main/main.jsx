import React, { useEffect } from "react";
import { Container } from "reactstrap";
import { Switch, Route } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { initRoute } from "redux/actions/init";
import { getInitRouteSelector } from "redux/selectors/init";
import { ConfirmationServiceProvider } from "components/confirmation-service/confirmation-service";
import { Login } from "components/login/login";
import { Calendar } from "pages/calendar/calendar";
import { Header } from "components/header/header";
import { Timeline } from "components/timeline/timeline";
import { Notification } from "components/notification/notification";
import { EventTypes } from "components/event-types/event-types";
import { Periods } from "components/periods/periods";

export function Main() {
  const dispatch = useDispatch();
  const isRouteInitialized = useSelector(getInitRouteSelector);

  useEffect(() => {
    dispatch(initRoute());
  }, [dispatch]);

  if (!isRouteInitialized) return null;

  return (
    <ConfirmationServiceProvider>
      <Switch>
        <Route exact path="/login">
          <Login />
        </Route>
        <Route path="/calendar">
          <Calendar />
        </Route>
        <Route path="/">
          <Container className="emblem-background" fluid>
            <Header />
            <Switch>
              <Route exact path="/">
                <Timeline />
              </Route>
              <Route exact path="/event_types">
                <EventTypes />
              </Route>
              <Route exact path="/periods">
                <Periods />
              </Route>
            </Switch>
          </Container>
        </Route>
      </Switch>
      <Notification />
    </ConfirmationServiceProvider>
  );
}
