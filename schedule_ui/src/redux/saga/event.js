import { call, put, takeEvery, all, select } from "redux-saga/effects";
import {
  CREATE_EVENT,
  UPDATE_EVENT,
  FETCH_LAST_EVENTS,
  REMOVE_EVENT,
  updateEvents,
} from "redux/actions/event";

import { getActiveCalendarSelector } from "redux/selectors/calendars";
import { closeForm, setErrorForm } from "redux/actions/forms";
import { fetchUnits } from "redux/saga/units";
import {
  createEvent as createEventAPI,
  updateEvent as updateEventAPI,
  removeEvent as removeEventAPI,
  getLastEvents,
} from "helpers/api";
import { NotificationManager } from "helpers/notification-manager";
import {
  SUCCESS_EVENT_NOTIFICATION_DATA,
  FAILED_EVENT_NOTIFICATION_DATA,
} from "constants/notifications";

function* createEvent(action) {
  const { event } = action.payload;
  const calendarId = yield select(getActiveCalendarSelector);

  try {
    yield call(createEventAPI, { ...event, calendarId });
    yield call(fetchUnits);
    yield put(closeForm());

    NotificationManager.fire(SUCCESS_EVENT_NOTIFICATION_DATA);
  } catch (e) {
    yield put(setErrorForm({ error: e }));

    NotificationManager.fire(FAILED_EVENT_NOTIFICATION_DATA);
  }
}

function* updateEvent(action) {
  const { event } = action.payload;
  const calendarId = yield select(getActiveCalendarSelector);

  try {
    yield call(updateEventAPI, { ...event, calendarId });
    yield call(fetchUnits);
    yield put(closeForm());

    NotificationManager.fire(SUCCESS_EVENT_NOTIFICATION_DATA);
  } catch (e) {
    yield put(setErrorForm({ error: e }));

    NotificationManager.fire(FAILED_EVENT_NOTIFICATION_DATA);
  }
}

function* removeEvent(action) {
  const { eventId } = action.payload;

  try {
    yield call(removeEventAPI, eventId);

    yield call(fetchUnits);
    yield put(closeForm());
  } catch (e) {
    NotificationManager.fire(FAILED_EVENT_NOTIFICATION_DATA);
  }
}

function* fetchLastEvents() {
  const calendarId = yield select(getActiveCalendarSelector);

  try {
    const events = yield call(getLastEvents, { calendarId });

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
  yield takeEvery(FETCH_LAST_EVENTS, fetchLastEvents);
}

function* removeEventSaga() {
  yield takeEvery(REMOVE_EVENT, removeEvent);
}

export default function* watchCalendars() {
  yield all([
    createEventSaga(),
    updateEventSaga(),
    removeEventSaga(),
    fetchEventsSaga(),
  ]);
}
