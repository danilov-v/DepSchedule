import {
  UPDATE_CALENDARS,
  UPDATE_ACTIVE_CALENDAR,
} from "redux/actions/calendars";
import { CLEAR_USER } from "redux/actions/auth";

const initialState = {
  calendars: [],
  activeCalendarId: null,
};

export const calendars = (state = initialState, action) => {
  switch (action.type) {
    case UPDATE_CALENDARS:
      return { ...state, calendars: [...action.payload.calendars] };
    case UPDATE_ACTIVE_CALENDAR:
      return {
        ...state,
        activeCalendarId: action.payload ? action.payload.calendarId : null,
      };
    case CLEAR_USER:
      return initialState;
    default:
      return state;
  }
};

export default calendars;
