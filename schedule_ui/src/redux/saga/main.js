import { all } from "redux-saga/effects";

import calendarsSaga from "redux/saga/calendars";
import authSaga from "redux/saga/auth";
import initSaga from "redux/saga/init";
import schedulerSaga from "redux/saga/scheduler";
import eventTypesSaga from "redux/saga/event-types";

export default function* rootSaga() {
  yield all([
    initSaga(),
    calendarsSaga(),
    authSaga(),
    schedulerSaga(),
    eventTypesSaga(),
  ]);
}
