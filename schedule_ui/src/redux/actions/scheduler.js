export const UPDATE_START_DATE = "UPDATE_START_DATE";
export const UPDATE_END_DATE = "UPDATE_END_DATE";
export const SET_OPERATIONAL_DATE = "SET_OPERATIONAL_DATE";
export const UPDATE_OPERATIONAL_DATE = "UPDATE_OPERATIONAL_DATE";
// UNITS
export const FETCH_UNITS = "FETCH_UNITS";
export const CREATE_UNIT = "CREATE_UNIT";
export const UPDATE_UNIT = "UPDATE_UNIT";
export const REMOVE_UNIT = "REMOVE_UNIT";
export const UPDATE_UNITS = "UPDATE_UNITS";
// EVENT TYPES
export const FETCH_EVENT_TYPES = "FETCH_EVENT_TYPES";
export const UPDATE_EVENT_TYPES = "UPDATE_EVENT_TYPES";
// PERIODS
export const FETCH_PERIODS = "FETCH_PERIODS";
export const CREATE_PERIOD = "CREATE_PERIOD";
export const EDIT_PERIOD = "EDIT_PERIOD";
export const REMOVE_PERIOD = "REMOVE_PERIOD";
export const UPDATE_PERIODS = "UPDATE_PERIODS";
// EVENTS
export const CREATE_EVENT = "CREATE_EVENT";
export const UPDATE_EVENT = "UPDATE_EVENT";
export const REMOVE_EVENT = "REMOVE_EVENT";
export const FETCH_LAST_EVENTS = "FETCH_LAST_EVENTS";
export const UPDATE_EVENTS = "UPDATE_EVENTS";

export const RESET_SCHEDULER = "RESET_SCHEDULER";

export const updateStartDate = date => ({
  type: UPDATE_START_DATE,
  payload: { date },
});

export const updateEndDate = date => ({
  type: UPDATE_END_DATE,
  payload: { date },
});

export const setOperationalDate = date => ({
  type: SET_OPERATIONAL_DATE,
  payload: { date },
});

export const updateOperationalDate = date => ({
  type: UPDATE_OPERATIONAL_DATE,
  payload: { date },
});

export const fetchUnits = payload => ({
  type: FETCH_UNITS,
  payload,
});

export const createUnit = payload => ({
  type: CREATE_UNIT,
  payload,
});

export const updateUnit = payload => ({
  type: UPDATE_UNIT,
  payload,
});

export const removeUnit = payload => ({
  type: REMOVE_UNIT,
  payload,
});

export const updateUnits = payload => ({
  type: UPDATE_UNITS,
  payload,
});

export const fetchPeriods = () => ({
  type: FETCH_PERIODS,
});

export const updatePeriods = periods => ({
  type: UPDATE_PERIODS,
  payload: {
    periods,
  },
});

export const createPeriod = period => ({
  type: CREATE_PERIOD,
  payload: {
    period,
  },
});

export const updatePeriod = period => ({
  type: EDIT_PERIOD,
  payload: {
    period,
  },
});

export const removePeriod = periodId => ({
  type: REMOVE_PERIOD,
  payload: {
    periodId,
  },
});

export const createEvent = payload => ({
  type: CREATE_EVENT,
  payload,
});

export const updateEvent = payload => ({
  type: UPDATE_EVENT,
  payload,
});

export const removeEvent = payload => ({
  type: REMOVE_EVENT,
  payload,
});

export const fetchLastEvents = () => ({
  type: FETCH_LAST_EVENTS,
});

export const updateEvents = events => ({
  type: UPDATE_EVENTS,
  payload: {
    events,
  },
});

export const resetScheduler = () => ({
  type: RESET_SCHEDULER,
});
