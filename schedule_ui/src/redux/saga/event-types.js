import { call, put, takeEvery, all } from "redux-saga/effects";
import {
  FETCH_EVENT_TYPES,
  CREATE_EVENT_TYPE,
  EDIT_EVENT_TYPE,
  REMOVE_EVENT_TYPE,
  updateEventTypes,
} from "redux/actions/event-types";
import { closeForm, setErrorForm } from "redux/actions/forms";
import {
  getEventTypes,
  createEventType,
  updateEventType,
  removeEventType as removeEventTypeAPI,
} from "helpers/api";

import {
  SUCCESS_EVENT_TYPE_NOTIFICATION_DATA,
  FAILED_EVENT_TYPE_NOTIFICATION_DATA,
} from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";

function* fetchEventTypes() {
  try {
    const eventTypes = yield call(getEventTypes);

    yield put(updateEventTypes(eventTypes));

    return eventTypes;
  } catch (e) {}
}

function* createEventTypes(action) {
  const { eventType } = action.payload;

  try {
    yield call(createEventType, eventType);
    yield put(closeForm());
    yield call(fetchEventTypes);
    NotificationManager.fire(SUCCESS_EVENT_TYPE_NOTIFICATION_DATA);
  } catch (error) {
    yield put(setErrorForm({ error }));
    NotificationManager.fire(FAILED_EVENT_TYPE_NOTIFICATION_DATA);
  }
}

function* editEventType(action) {
  const { eventType } = action.payload;

  try {
    yield call(updateEventType, eventType);
    yield put(closeForm());
    NotificationManager.fire(SUCCESS_EVENT_TYPE_NOTIFICATION_DATA);

    yield call(fetchEventTypes);
  } catch (error) {
    yield put(setErrorForm({ error }));
    NotificationManager.fire(FAILED_EVENT_TYPE_NOTIFICATION_DATA);
  }
}

function* removeEventType(action) {
  const { eventTypeId } = action.payload;

  try {
    yield call(removeEventTypeAPI, eventTypeId);
    yield call(fetchEventTypes);
  } catch (error) {
    yield put(setErrorForm({ error }));
    NotificationManager.fire(FAILED_EVENT_TYPE_NOTIFICATION_DATA);
  }
}

function* fetchEventTypesSaga() {
  yield takeEvery(FETCH_EVENT_TYPES, fetchEventTypes);
}

function* createEventTypeSaga() {
  yield takeEvery(CREATE_EVENT_TYPE, createEventTypes);
}

function* updateEventTypeSaga() {
  yield takeEvery(EDIT_EVENT_TYPE, editEventType);
}

function* removeEventTypeSaga() {
  yield takeEvery(REMOVE_EVENT_TYPE, removeEventType);
}

export default function* watchEventTypes() {
  yield all([
    fetchEventTypesSaga(),
    createEventTypeSaga(),
    updateEventTypeSaga(),
    removeEventTypeSaga(),
  ]);
}
