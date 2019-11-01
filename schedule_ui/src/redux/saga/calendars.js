import { call, put, takeEvery, all, select } from "redux-saga/effects";
import { differenceInCalendarDays } from "date-fns";
import {
  FETCH_CALENDARS,
  CREATE_CALENDAR,
  REMOVE_CALENDAR,
  ACTIVE_CALENDAR,
  UPDATE_CALENDAR,
  updateCalendars,
  updateActiveCalendar,
  setActiveCalendar,
} from "redux/actions/calendars";
import { resetData } from "redux/actions/ui";
import {
  getActiveCalendarSelector,
  getActiveCalendarInfo,
} from "redux/selectors/calendars";
import { handleErrorRequest } from "redux/saga/auth";
import {
  getCalendars,
  createCalendar,
  removeCalendar as removeCalendarAPI,
  updateCalendar as updateCalendarAPI,
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
    yield call(history.push, "/");
  } else {
    yield call(history.push, "/calendar");
    yield put(resetData());
  }
}

function* updateCalendar(action) {
  const { calendarId, operationalDate, ...payload } = action.payload;
  const activeCalendarId = calendarId
    ? calendarId
    : yield select(getActiveCalendarSelector);
  const { startDate } = yield select(getActiveCalendarInfo);
  const shift = differenceInCalendarDays(operationalDate, startDate);

  try {
    yield call(updateCalendarAPI, activeCalendarId, { shift, ...payload });

    yield call(fetchCalendars);
  } catch (error) {
    debugger;
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

function* updateCalendarSaga() {
  yield takeEvery(UPDATE_CALENDAR, updateCalendar);
}

export default function* watchCalendars() {
  yield all([
    fetchCalendarsSaga(),
    createCalendarSaga(),
    removeCalendarSaga(),
    selectCalendarsSaga(),
    updateCalendarSaga(),
  ]);
}
