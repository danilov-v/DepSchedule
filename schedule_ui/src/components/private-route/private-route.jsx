import React from "react";
import { Redirect, Route } from "react-router";
import { useAuth } from "components/auth-service/auth-service";
import { isEmpty } from "lodash";

export function PrivateRoute({ render, ...routeProps }) {
  const { authBody } = useAuth();

  return (
    <Route
      {...routeProps}
      render={isEmpty(authBody) ? () => <Redirect to="login" /> : render}
    />
  );
}
