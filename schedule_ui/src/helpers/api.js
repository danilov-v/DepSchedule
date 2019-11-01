import { format } from "date-fns";
import {
  LOCALHOST_URL,
  DEFAULT_BE_PORT,
  API,
  UNITS_URL,
  EVENT_URL,
  EVENT_TYPE_URL,
  EVENT_RECENT_LIST,
  PERIODS,
  LOGIN,
  LOGOUT,
  CALENDAR,
} from "constants/url";
import { getToken } from "helpers/localstorage";

const buildToken = token => (token ? `Bearer ${token}` : "");

const makeApiCall = async (apiPath, params) => {
  const url = `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${API}/${apiPath}`;
  const token = buildToken(getToken());
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
    //EMPTY RESPONSE BODY CAUSE ERROR IN result.json();
    data = {};
  }

  if (result && result.status !== 200) {
    throw data;
  }

  return data;
};

export const signIn = async userCred =>
  await makeApiCall(LOGIN, {
    method: "PATCH",
    body: JSON.stringify(userCred),
    headers: {
      "Content-Type": "application/json",
    },
  });

export const signOut = async () =>
  await makeApiCall(LOGOUT, {
    method: "PATCH",
  });

export const getLastEvents = async ({ calendarId }) =>
  await makeApiCall(
    `${EVENT_URL}/recentList/?calendarId=${calendarId}&count=${50}`
  );

export const getUnits = async () => await makeApiCall(UNITS_URL);

export const getUnitsTree = async ({ calendarId }) =>
  await makeApiCall(`${UNITS_URL}/tree?calendarId=${calendarId}`, {});

export const createUnit = async unitData =>
  await makeApiCall(UNITS_URL, {
    method: "POST",
    body: JSON.stringify(unitData),
  });

export const updateUnit = async unitData =>
  await makeApiCall(`${UNITS_URL}/${unitData.unitId}`, {
    method: "PATCH",
    body: JSON.stringify(unitData),
  });

export const deleteUnit = async unitId =>
  await makeApiCall(`${UNITS_URL}/${unitId}`, {
    method: "DELETE",
  });

export const getEventTypes = async () => await makeApiCall(EVENT_TYPE_URL, {});

export const createEventType = async eventTypeData =>
  await makeApiCall(EVENT_TYPE_URL, {
    method: "POST",
    body: JSON.stringify(eventTypeData),
  });

export const getFinishedEvents = async count =>
  await makeApiCall(`${EVENT_RECENT_LIST}/?count=${count}`);

export const updateEventType = async eventTypeData =>
  await makeApiCall(`${EVENT_TYPE_URL}/${eventTypeData.typeId}`, {
    method: "PATCH",
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
    method: "PATCH",
    body: JSON.stringify(eventData),
  });

export const removeEvent = async eventId =>
  await makeApiCall(`${EVENT_URL}/${eventId}`, {
    method: "DELETE",
  });

/**
 * PERIODS API
 */
export const getPeriods = async () => await makeApiCall(PERIODS, {});

export const createPeriod = async periodData =>
  await makeApiCall(PERIODS, {
    method: "POST",
    body: JSON.stringify({
      ...periodData,
      startDate: format(periodData.startDate, "yyyy-MM-dd"),
      endDate: format(periodData.endDate, "yyyy-MM-dd"),
    }),
  });

export const updatePeriod = async periodData =>
  await makeApiCall(`${PERIODS}/${periodData.periodId}`, {
    method: "PATCH",
    body: JSON.stringify(periodData),
  });

export const removePeriod = async periodId =>
  await makeApiCall(`${PERIODS}/${periodId}`, {
    method: "DELETE",
  });

/**
 * END PERIODS API
 */

/**
 * CALENDARS API
 */
export const getCalendars = async () => await makeApiCall(CALENDAR, null);

export const updateCalendar = async (calendarId, payload) =>
  await makeApiCall(`${CALENDAR}/${calendarId}`, {
    method: "PATCH",
    body: JSON.stringify(payload),
  });

export const createCalendar = async ({ calendar }) =>
  await makeApiCall(CALENDAR, {
    method: "POST",
    body: JSON.stringify(calendar),
  });

export const removeCalendar = async ({ calendarId }) =>
  makeApiCall(`${CALENDAR}/${calendarId}`, {
    method: "DELETE",
  });
/**
 * END CALENDARS API
 */
