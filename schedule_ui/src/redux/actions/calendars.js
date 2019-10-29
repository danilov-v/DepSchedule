export const FETCH_CALENDARS = "FETCH_CALENDARS";
export const CREATE_CALENDAR = "CREATE_CALENDAR";
export const UPDATE_CALENDARS = "UPDATE_CALENDARS";
export const ACTIVE_CALENDAR = "ACTIVE_CALENDAR";
export const UPDATE_ACTIVE_CALENDAR = "UPDATE_ACTIVE_CALENDAR";
export const REMOVE_CALENDAR = "REMOVE_CALENDAR";

export const fetchCalendars = payload => ({
  type: FETCH_CALENDARS,
  payload,
});

export const createCalendar = payload => ({
  type: CREATE_CALENDAR,
  payload,
});

export const updateCalendars = calendars => ({
  type: UPDATE_CALENDARS,
  payload: {
    calendars,
  },
});

export const setActiveCalendar = payload => ({
  type: ACTIVE_CALENDAR,
  payload,
});

export const updateActiveCalendar = payload => ({
  type: UPDATE_ACTIVE_CALENDAR,
  payload,
});

export const removeCalendar = payload => ({
  type: REMOVE_CALENDAR,
  payload,
});
