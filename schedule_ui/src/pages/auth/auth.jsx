import React from "react";
import { Home } from "components/home/home";
import { Switch, Route } from "react-router-dom";
import {
  AuthServiceProvider,
  AuthPage,
} from "components/auth-service/auth-service";
import { PrivateRoute } from "components/private-route/private-route";
import { Notification } from "components/notification/notification";

export function Auth() {
  return (
    <AuthServiceProvider>
      <Switch>
        <Route exact path="/login" render={() => <AuthPage />} />
        <PrivateRoute path="/" render={() => <Home />} />
      </Switch>
      <Notification />
    </AuthServiceProvider>
  );
}
