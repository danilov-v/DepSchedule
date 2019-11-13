import { call, put, takeEvery, all, select } from "redux-saga/effects";
import {
  FETCH_UNITS,
  CREATE_UNIT,
  UPDATE_UNIT,
  REMOVE_UNIT,
  updateUnits,
} from "redux/actions/units";
import { handleErrorRequest } from "redux/saga/auth";
import { getActiveCalendarSelector } from "redux/selectors/calendars";
import { closeForm } from "redux/actions/forms";
import {
  getUnitsTree,
  createUnit as createUnitAPI,
  updateUnit as updateUnitAPI,
  deleteUnit as deleteUnitAPI,
} from "helpers/api";
import { NotificationManager } from "helpers/notification-manager";
import {
  SUCCESS_UNIT_NOTIFICATION_DATA,
  SUCCESS_UNIT_NOTIFICATION_DATA_EDIT,
  SUCCESS_UNIT_NOTIFICATION_DATA_DELETE,
  FAILED_UNIT_NOTIFICATION_DATA,
} from "constants/notifications";

export function* fetchUnits() {
  const calendarId = yield select(getActiveCalendarSelector);

  try {
    const unitsTree = yield call(getUnitsTree, {
      calendarId,
    });

    yield put(updateUnits({ units: unitsTree }));
  } catch (e) {
    yield call(handleErrorRequest, e);
    // TODO - remove session from store and update url
  }
}

function* createUnit(action) {
  const { unit } = action.payload;
  const calendarId = yield select(getActiveCalendarSelector);

  try {
    yield call(createUnitAPI, { ...unit, calendarId });

    NotificationManager.fire(SUCCESS_UNIT_NOTIFICATION_DATA);

    yield put(closeForm());

    yield call(fetchUnits);
  } catch (error) {
    console.error("Create unit failed.", error);
    NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
  }
}
function* updateUnit(action) {
  const { unit } = action.payload;
  const calendarId = yield select(getActiveCalendarSelector);

  try {
    yield call(updateUnitAPI, { ...unit, calendarId });

    NotificationManager.fire(SUCCESS_UNIT_NOTIFICATION_DATA_EDIT);

    yield put(closeForm());

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

export default function* watchCalendars() {
  yield all([
    fetchUnitsSaga(),
    createUnitSaga(),
    updateUnitSaga(),
    removeUnitSaga(),
  ]);
}
