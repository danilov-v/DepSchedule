import { UPDATE_EVENTS } from "redux/actions/event";
import { RESET } from "redux/actions/ui";
import { CLEAR_USER } from "redux/actions/auth";

const initialState = {
  events: [],
};

export const event = (state = initialState, action) => {
  switch (action.type) {
    case UPDATE_EVENTS:
      return { ...state, events: [...action.payload.events] };

    case CLEAR_USER:
    case RESET:
      return initialState;
    default:
      return state;
  }
};

export default event;
