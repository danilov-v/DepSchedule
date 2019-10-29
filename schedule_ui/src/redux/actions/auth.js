export const LOGIN_REQUEST = "LOGIN_REQUEST";
export const LOGIN_REQUEST_FAILED = "LOGIN_REQUEST_FAILED";
export const LOGOUT_REQUEST = "LOGOUT_REQUEST";

export const UPDATE_USER = "UPDATE_USER";
export const CLEAR_USER = "CLEAR_USER";

export const loginRequest = payload => ({
  type: LOGIN_REQUEST,
  payload,
});

export const logoutRequest = () => ({
  type: LOGOUT_REQUEST,
});

export const loginRequestFailed = error => ({
  type: LOGIN_REQUEST_FAILED,
  error,
});

export const updateUser = payload => ({
  type: UPDATE_USER,
  payload,
});

export const clearUser = () => ({
  type: CLEAR_USER,
});
