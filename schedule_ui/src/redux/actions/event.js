// EVENTS
export const CREATE_EVENT = "CREATE_EVENT";
export const UPDATE_EVENT = "UPDATE_EVENT";
export const REMOVE_EVENT = "REMOVE_EVENT";
export const FETCH_LAST_EVENTS = "FETCH_LAST_EVENTS";
export const UPDATE_EVENTS = "UPDATE_EVENTS";

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
