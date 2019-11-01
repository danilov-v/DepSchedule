import { all } from "redux-saga/effects";

import calendarsSaga from "redux/saga/calendars";
import authSaga from "redux/saga/auth";
import initSaga from "redux/saga/init";
import periodsSaga from "redux/saga/periods";
import unitsSaga from "redux/saga/units";
import eventTypesSaga from "redux/saga/event-types";
import eventSaga from "redux/saga/event";

export default function* rootSaga() {
  yield all([
    initSaga(),
    calendarsSaga(),
    authSaga(),
    periodsSaga(),
    unitsSaga(),
    eventTypesSaga(),
    eventSaga(),
  ]);
}
