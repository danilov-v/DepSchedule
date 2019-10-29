export const FETCH_EVENT_TYPES = "FETCH_EVENT_TYPES";
export const CREATE_EVENT_TYPE = "CREATE_EVENT_TYPE";
export const EDIT_EVENT_TYPE = "EDIT_EVENT_TYPE";
export const UPDATE_EVENT_TYPES = "UPDATE_EVENT_TYPES";
export const REMOVE_EVENT_TYPE = "REMOVE_EVENT_TYPE";

export const fetchEventTypes = payload => ({
  type: FETCH_EVENT_TYPES,
  payload,
});

export const createEventType = eventType => ({
  type: CREATE_EVENT_TYPE,
  payload: {
    eventType,
  },
});

export const editEventType = eventType => ({
  type: EDIT_EVENT_TYPE,
  payload: {
    eventType,
  },
});

export const updateEventTypes = eventTypes => ({
  type: UPDATE_EVENT_TYPES,
  payload: {
    eventTypes,
  },
});

export const removeEventType = eventTypeId => ({
  type: REMOVE_EVENT_TYPE,
  payload: {
    eventTypeId,
  },
});
