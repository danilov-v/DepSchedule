import { UPDATE_PERIODS } from "redux/actions/periods";
import { RESET } from "redux/actions/ui";
import { CLEAR_USER } from "redux/actions/auth";

const initialState = {
  periods: [],
};

export const periods = (state = initialState, action) => {
  switch (action.type) {
    case UPDATE_PERIODS:
      return { ...state, periods: [...action.payload.periods] };

    case CLEAR_USER:
    case RESET:
      return initialState;
    default:
      return state;
  }
};

export default periods;
