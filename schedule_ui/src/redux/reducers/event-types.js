import { UPDATE_EVENT_TYPES } from "redux/actions/event-types";

const initialState = {
  eventTypes: [],
};

export const eventTypes = (state = initialState, action) => {
  switch (action.type) {
    case UPDATE_EVENT_TYPES:
      return { ...state, eventTypes: [...action.payload.eventTypes] };
    default:
      return state;
  }
};

export default eventTypes;
