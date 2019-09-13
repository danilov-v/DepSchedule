import { format } from "date-fns";
import {
  LOCALHOST_URL,
  DEFAULT_BE_PORT,
  API,
  UNITS_URL,
  EVENT_URL,
  EVENT_TYPE_URL,
  PERIODS,
  LOGIN,
  LOGOUT,
} from "config/url";

const makeApiCall = async (apiPath, params) => {
  const url = `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${API}/${apiPath}`;
  const authBody = JSON.parse(window.localStorage.getItem("authBody"));
  const token = authBody ? `Bearer ${authBody.token}` : "";
  const request = new Request(url, {
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      Authorization: token,
    },
    ...params,
  });

  const result = await fetch(request);
  let data;

  try {
    data = await result.json();
  } catch (error) {
    data = {};
  }

  if (result && result.status !== 200) {
    throw data;
  }

  return data;
};

export const signIn = async userCred =>
  await makeApiCall(LOGIN, {
    method: "PUT",
    body: JSON.stringify(userCred),
    headers: {
      "Content-Type": "application/json",
    },
  });

export const signOut = async () =>
  await makeApiCall(LOGOUT, {
    method: "PUT",
  });

export const getUnits = async () => await makeApiCall(UNITS_URL);

export const getUnitsTree = async dateFrom =>
  await makeApiCall(
    `${UNITS_URL}/tree?dateFrom=${format(dateFrom, "yyyy-MM-dd")}`
  );

export const createUnit = async unitData =>
  await makeApiCall(UNITS_URL, {
    method: "POST",
    body: JSON.stringify(unitData),
  });

export const updateUnit = async unitData =>
  await makeApiCall(`${UNITS_URL}/${unitData.unitId}`, {
    method: "PUT",
    body: JSON.stringify(unitData),
  });

export const deleteUnit = async unitId =>
  await makeApiCall(`${UNITS_URL}/${unitId}`, {
    method: "DELETE",
  });

export const getEventTypes = async () => await makeApiCall(EVENT_TYPE_URL);

export const createEventType = async eventTypeData =>
  await makeApiCall(EVENT_TYPE_URL, {
    method: "POST",
    body: JSON.stringify(eventTypeData),
  });

export const updateEventType = async eventTypeData =>
  await makeApiCall(`${EVENT_TYPE_URL}/${eventTypeData.typeId}`, {
    method: "PUT",
    body: JSON.stringify(eventTypeData),
  });

export const removeEventType = async typeId =>
  await makeApiCall(`${EVENT_TYPE_URL}/${typeId}`, {
    method: "DELETE",
  });

export const createEvent = async eventData =>
  await makeApiCall(EVENT_URL, {
    method: "POST",
    body: JSON.stringify(eventData),
  });

export const updateEvent = async eventData =>
  await makeApiCall(`${EVENT_URL}/${eventData.eventId}`, {
    method: "PUT",
    body: JSON.stringify(eventData),
  });

export const removeEvent = async eventId =>
  await makeApiCall(`${EVENT_URL}/${eventId}`, {
    method: "DELETE",
  });

/**
 * PERIODS API
 */
export const getPeriods = async () => await makeApiCall(PERIODS);

export const createPeriod = async periodData =>
  await makeApiCall(PERIODS, {
    method: "POST",
    body: JSON.stringify(periodData),
  });

export const updatePeriod = async periodData =>
  await makeApiCall(`${PERIODS}/${periodData.periodId}`, {
    method: "PUT",
    body: JSON.stringify(periodData),
  });

export const removePeriod = async periodId =>
  await makeApiCall(`${PERIODS}/${periodId}`, {
    method: "DELETE",
  });

/**
 * END PERIODS API
 */
