import { call, put, takeEvery, all, select } from "redux-saga/effects";
import {
  LOGIN_REQUEST,
  LOGOUT_REQUEST,
  loginRequestFailed,
  updateUser,
  clearUser,
} from "redux/actions/auth";
import { resetScheduler } from "redux/actions/scheduler";
import { history } from "helpers/history";
import { signIn, signOut } from "helpers/api";
import { setToken } from "helpers/localstorage";
import { getAuthData as getAuthDataSelector } from "redux/selectors/auth";

export function* getAuthData() {
  const authData = yield select(getAuthDataSelector);

  return authData;
}

function* login(action) {
  const { user } = action.payload;

  try {
    const loginData = yield call(signIn, user);

    yield put(updateUser(loginData));
    yield call(setToken, loginData.token);
    yield call(history.push, "/calendar");
  } catch (e) {
    console.error("LOGIN ERROR", e);
    yield put(loginRequestFailed(e));
  }
}

function* logout() {
  try {
    yield call(signOut);
    yield call(setToken, null);
    yield call(history.push, "/login");

    yield put(clearUser());
    yield put(resetScheduler());
  } catch (e) {
    console.error("LOGOUT ERROR", e);
  }
}

function* loginRequest() {
  yield takeEvery(LOGIN_REQUEST, login);
}

function* logoutRequest() {
  yield takeEvery(LOGOUT_REQUEST, logout);
}

export default function* watchAuth() {
  yield all([loginRequest(), logoutRequest()]);
}

export function* handleErrorRequest(error) {
  if (error && error.code === "403") {
    yield put(clearUser());
    yield call(setToken, null);
    yield call(history.push, "/login");
  }
}
