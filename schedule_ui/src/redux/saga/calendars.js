import { call, put, takeEvery, all, select } from "redux-saga/effects";
import { addDays } from "date-fns";
import {
  FETCH_CALENDARS,
  CREATE_CALENDAR,
  REMOVE_CALENDAR,
  ACTIVE_CALENDAR,
  updateCalendars,
  updateActiveCalendar,
  setActiveCalendar,
} from "redux/actions/calendars";
import {
  resetScheduler,
  updateStartDate,
  updateEndDate,
  updateOperationalDate,
} from "redux/actions/scheduler";
import { getActiveCalendar } from "redux/selectors/calendars";
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

  try {
    yield call(removeCalendarAPI, { calendarId });

    yield call(fetchCalendars);
  } catch (e) {}
}

function* selectCalendar(action) {
  const { calendarId } = action.payload;

  yield put(updateActiveCalendar({ calendarId: calendarId }));

  if (calendarId) {
    yield call(setDatesAccordingCalendar);
    yield call(history.push, "/");
  } else {
    yield call(history.push, "/calendar");
    yield put(resetScheduler());
  }
}

export function* initCalendar(action) {
  yield call(fetchCalendars);
  yield call(setDatesAccordingCalendar);
}

function* setDatesAccordingCalendar() {
  const activeCalendar = yield select(getActiveCalendar);

  if (activeCalendar) {
    const startDate = activeCalendar.dateFrom
      ? new Date(activeCalendar.dateFrom)
      : new Date();
    const endDate = activeCalendar.dateTo && new Date(activeCalendar.dateTo);

    if (startDate) yield put(updateStartDate(startDate));
    if (endDate) yield put(updateEndDate(endDate));
    yield put(updateOperationalDate(addDays(startDate, activeCalendar.shift)));
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
