import React, { useState, useContext, createContext, useEffect } from "react";
import { Redirect } from "react-router-dom";
import { isString, isEmpty, get } from "lodash";
import { signIn, signOut } from "helpers/api";
import { NotificationManager } from "helpers/notification-manager";
import { FAILED_LOGIN } from "constants/notifications";
import { Login } from "./login";

export const DEFAULT_AUTH_DATA = {
  authBody: JSON.parse(window.localStorage.getItem("authBody")) || {},
};
const AuthServiceContext = createContext({});

export const useAuth = () => useContext(AuthServiceContext);

export const AuthServiceProvider = ({ children }) => {
  const [authBody, setAuthBody] = useState(DEFAULT_AUTH_DATA.authBody);

  useEffect(() => {
    window.localStorage.setItem(
      "authBody",
      isString(authBody) ? authBody : JSON.stringify(authBody)
    );
  }, [authBody]);

  const login = async userCred => {
    try {
      const data = await signIn(userCred);
      setAuthBody(data);
    } catch (e) {
      NotificationManager.fire(FAILED_LOGIN);
      throw e;
    }
  };

  const logout = async () => {
    setAuthBody({});

    try {
      await signOut();
    } catch (e) {
      console.group("failed server logout with error: ");
      console.log(e);
      console.groupEnd();
    }
  };

  const getRole = () => get(authBody, "role");

  const defaultContext = {
    authBody,
    getRole,
    login,
    logout,
  };

  return (
    <AuthServiceContext.Provider value={defaultContext} children={children} />
  );
};

export const AuthPage = () => {
  const { authBody, login } = useAuth();

  return isEmpty(authBody) ? <Login login={login} /> : <Redirect to="/" />;
};
