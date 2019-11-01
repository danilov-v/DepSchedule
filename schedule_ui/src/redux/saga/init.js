import { call, takeEvery, all, select, put } from "redux-saga/effects";
import { INIT_ROUTE, routeInitialized } from "redux/actions/init";
import { history } from "helpers/history";
import { getAuthData } from "redux/saga/auth";
import { getActiveCalendarSelector } from "redux/selectors/calendars";

function* initRoute() {
  const authData = yield call(getAuthData);
  const activeCalendarId = yield select(getActiveCalendarSelector);

  switch (history.location.pathname) {
    case "/login":
      if (authData.token) {
        yield call(history.replace, activeCalendarId ? "/" : "/calendar");
      }
      break;
    case "/":
    case "/event_types":
    case "/periods":
      if (!authData.token) yield call(history.replace, "/login");
      else if (!activeCalendarId) yield call(history.replace, "/calendar");
      break;
    case "/calendar":
      if (!authData.token) yield call(history.replace, "/login");
      break;
    default:
      if (!authData.token) yield call(history.replace, "/login");
      else if (!activeCalendarId) yield call(history.replace, "/calendar");
      else yield call(history.replace, "/");
      break;
  }
  yield put(routeInitialized());
}

function* initApp() {
  yield takeEvery(INIT_ROUTE, initRoute);
}

export default function* watchInit() {
  yield all([initApp()]);
}
