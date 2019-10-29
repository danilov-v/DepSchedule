import { call, put, takeEvery, all } from "redux-saga/effects";
import {
  FETCH_CALENDARS,
  CREATE_CALENDAR,
  REMOVE_CALENDAR,
  ACTIVE_CALENDAR,
  updateCalendars,
  updateActiveCalendar,
  setActiveCalendar,
} from "redux/actions/calendars";
import { resetScheduler } from "redux/actions/scheduler";
import { handleErrorRequest } from "redux/saga/auth";
import {
  getCalendars,
  createCalendar,
  removeCalendar as removeCalendarAPI,
} from "helpers/api";
import { history } from "helpers/history";

function* fetchCalendars() {
  try {
    const calendars = yield call(getCalendars);

    yield put(updateCalendars(calendars));

    return calendars;
  } catch (e) {
    yield call(handleErrorRequest, e);
    // TODO - remove session from store and update url
  }
}

function* createCalendars(action) {
  const { calendar } = action.payload;

  try {
    const createdCalendar = yield call(createCalendar, { calendar });
    console.log(createdCalendar);

    yield call(fetchCalendars);

    yield put(setActiveCalendar({ calendarId: createdCalendar.calendarId }));
  } catch (e) {}
}

function* removeCalendar(action) {
  const { calendarId } = action.payload;
  console.log(calendarId);

  try {
    yield call(removeCalendarAPI, { calendarId });

    yield call(fetchCalendars);
  } catch (e) {}
}

function* selectCalendar(action) {
  const { calendarId } = action.payload;

  yield put(updateActiveCalendar({ calendarId: calendarId }));

  if (calendarId) {
    yield call(history.push, "/");
  } else {
    yield call(history.push, "/calendar");
    yield put(resetScheduler());
  }
}

function* fetchCalendarsSaga() {
  yield takeEvery(FETCH_CALENDARS, fetchCalendars);
}

function* createCalendarSaga() {
  yield takeEvery(CREATE_CALENDAR, createCalendars);
}

function* selectCalendarsSaga() {
  yield takeEvery(ACTIVE_CALENDAR, selectCalendar);
}

function* removeCalendarSaga() {
  yield takeEvery(REMOVE_CALENDAR, removeCalendar);
}

export default function* watchCalendars() {
  yield all([
    fetchCalendarsSaga(),
    createCalendarSaga(),
    removeCalendarSaga(),
    selectCalendarsSaga(),
  ]);
}
