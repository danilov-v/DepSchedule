import { call, put, takeEvery, all, select } from "redux-saga/effects";
import { differenceInCalendarDays } from "date-fns";
import {
  FETCH_UNITS,
  CREATE_UNIT,
  UPDATE_UNIT,
  REMOVE_UNIT,
  FETCH_PERIODS,
  CREATE_PERIOD,
  EDIT_PERIOD,
  REMOVE_PERIOD,
  CREATE_EVENT,
  UPDATE_EVENT,
  FETCH_LAST_EVENTS,
  REMOVE_EVENT,
  SET_OPERATIONAL_DATE,
  updateUnits,
  updateEvents,
  updatePeriods,
  updateOperationalDate,
} from "redux/actions/scheduler";
import { fetchCalendars } from "redux/actions/calendars";
import { periodRequestFail, closePeriodForm } from "redux/actions/forms";
import { handleErrorRequest } from "redux/saga/auth";
import { getActiveCalendarSelector } from "redux/selectors/calendars";
import { getStartDateSelector } from "redux/selectors/scheduler";
import { closeEventForm, getFailedEventAction } from "redux/actions/forms";
import {
  getUnitsTree,
  createUnit as createUnitAPI,
  updateUnit as updateUnitAPI,
  deleteUnit as deleteUnitAPI,
  createEvent as createEventAPI,
  updateEvent as updateEventAPI,
  removeEvent as removeEventAPI,
  getPeriods,
  createPeriod,
  updatePeriod,
  removePeriod as removePeriodAPI,
  updateCalendar,
  getLastEvents,
} from "helpers/api";
import { NotificationManager } from "helpers/notification-manager";
import {
  SUCCESS_UNIT_NOTIFICATION_DATA,
  SUCCESS_UNIT_NOTIFICATION_DATA_EDIT,
  SUCCESS_EVENT_NOTIFICATION_DATA,
  SUCCESS_UNIT_NOTIFICATION_DATA_DELETE,
  FAILED_UNIT_NOTIFICATION_DATA,
  FAILED_EVENT_NOTIFICATION_DATA,
  SUCCESS_PERIOD_NOTIFICATION_DATA,
  FAILED_PERIOD_NOTIFICATION_DATA,
} from "constants/notifications";

function* fetchUnits() {
  const dateFrom = yield select(getStartDateSelector);

  try {
    const unitsTree = yield call(getUnitsTree, { dateFrom });

    yield put(updateUnits({ units: unitsTree }));
  } catch (e) {
    yield call(handleErrorRequest, e);
    // TODO - remove session from store and update url
  }
}

function* createUnit(action) {
  const { unit } = action.payload;

  try {
    yield call(createUnitAPI, unit);

    NotificationManager.fire(SUCCESS_UNIT_NOTIFICATION_DATA);

    yield call(fetchUnits);
  } catch (error) {
    console.error("Create unit failed.", error);
    NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
  }
}
function* updateUnit(action) {
  const { unit } = action.payload;

  try {
    yield call(updateUnitAPI, unit);

    NotificationManager.fire(SUCCESS_UNIT_NOTIFICATION_DATA_EDIT);

    yield call(fetchUnits);
  } catch (error) {
    console.error("Create update failed.", error);
    NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
  }
}
function* removeUnit(action) {
  const { unitId } = action.payload;

  try {
    yield call(deleteUnitAPI, unitId);

    NotificationManager.fire(SUCCESS_UNIT_NOTIFICATION_DATA_DELETE);

    yield call(fetchUnits);
  } catch (error) {
    console.error("Create update failed.", error);
    NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
  }
}

function* fetchPeriods(action) {
  try {
    const periods = yield call(getPeriods);
    yield put(updatePeriods(periods));
  } catch (e) {
    // TODO - remove session from store and update url
  }
}

function* createPeriods(action) {
  const { period } = action.payload;

  try {
    yield call(createPeriod, period);
    yield call(fetchPeriods);
    yield put(closePeriodForm());
    NotificationManager.fire(SUCCESS_PERIOD_NOTIFICATION_DATA);
  } catch (e) {
    yield put(periodRequestFail(e));
    NotificationManager.fire(FAILED_PERIOD_NOTIFICATION_DATA);

    //NOTIFY USER ABOUT PERIODS INTERSCEPTION
  }
}

function* editPeriod(action) {
  const { period } = action.payload;

  try {
    yield call(updatePeriod, period);
    yield call(fetchPeriods);
    yield put(closePeriodForm());
    NotificationManager.fire(SUCCESS_PERIOD_NOTIFICATION_DATA);
  } catch (e) {
    yield put(periodRequestFail(e));
    NotificationManager.fire(FAILED_PERIOD_NOTIFICATION_DATA);

    //NOTIFY USER ABOUT PERIODS INTERSCEPTION
  }
}

function* removePeriod(action) {
  const { periodId } = action.payload;

  try {
    yield call(removePeriodAPI, periodId);

    yield call(fetchPeriods);
  } catch (e) {}
}

function* createEvent(action) {
  const { event } = action.payload;

  try {
    yield call(createEventAPI, event);
    yield call(fetchUnits);
    yield put(closeEventForm());

    NotificationManager.fire(SUCCESS_EVENT_NOTIFICATION_DATA);
  } catch (e) {
    yield put(getFailedEventAction({ error: e }));

    NotificationManager.fire(FAILED_EVENT_NOTIFICATION_DATA);
  }
}

function* updateEvent(action) {
  const { event } = action.payload;

  try {
    yield call(updateEventAPI, event);
    yield call(fetchUnits);
    yield put(closeEventForm());

    NotificationManager.fire(SUCCESS_EVENT_NOTIFICATION_DATA);
  } catch (e) {
    yield put(getFailedEventAction({ error: e }));

    NotificationManager.fire(FAILED_EVENT_NOTIFICATION_DATA);
  }
}

function* removeEvent(action) {
  const { eventId } = action.payload;

  try {
    yield call(removeEventAPI, eventId);

    yield call(fetchUnits);
    yield put(closeEventForm());
  } catch (e) {
    NotificationManager.fire(FAILED_EVENT_NOTIFICATION_DATA);
  }
}

function* setOperationalDate(action) {
  const { date } = action.payload;
  const activeCalendarId = yield select(getActiveCalendarSelector);
  const shift = differenceInCalendarDays(date, new Date());

  yield put(updateOperationalDate(date));
  yield call(updateCalendar, activeCalendarId, shift);
  // TODO: Improve this
  yield put(fetchCalendars());
}

function* fetchUnitsSaga() {
  yield takeEvery(FETCH_UNITS, fetchUnits);
}

function* createUnitSaga() {
  yield takeEvery(CREATE_UNIT, createUnit);
}
function* updateUnitSaga() {
  yield takeEvery(UPDATE_UNIT, updateUnit);
}
function* removeUnitSaga() {
  yield takeEvery(REMOVE_UNIT, removeUnit);
}

function* fetchPeriodsSaga() {
  yield takeEvery(FETCH_PERIODS, fetchPeriods);
}

function* createPeriodSaga() {
  yield takeEvery(CREATE_PERIOD, createPeriods);
}

function* editPeriodSaga() {
  yield takeEvery(EDIT_PERIOD, editPeriod);
}

function* removePeriodSaga() {
  yield takeEvery(REMOVE_PERIOD, removePeriod);
}

function* fetchEvents() {
  try {
    const events = yield call(getLastEvents);

    yield put(updateEvents(events));
  } catch (e) {
    // TODO - remove session from store and update url
  }
}

function* createEventSaga() {
  yield takeEvery(CREATE_EVENT, createEvent);
}

function* updateEventSaga() {
  yield takeEvery(UPDATE_EVENT, updateEvent);
}

function* fetchEventsSaga() {
  yield takeEvery(FETCH_LAST_EVENTS, fetchEvents);
}

function* removeEventSaga() {
  yield takeEvery(REMOVE_EVENT, removeEvent);
}

function* setOperationalDateSaga() {
  yield takeEvery(SET_OPERATIONAL_DATE, setOperationalDate);
}

export default function* watchCalendars() {
  yield all([
    fetchUnitsSaga(),
    fetchPeriodsSaga(),
    createUnitSaga(),
    updateUnitSaga(),
    removeUnitSaga(),
    createPeriodSaga(),
    editPeriodSaga(),
    removePeriodSaga(),
    createEventSaga(),
    updateEventSaga(),
    removeEventSaga(),
    fetchEventsSaga(),
    setOperationalDateSaga(),
  ]);
}
