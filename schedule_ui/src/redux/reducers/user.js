import { format } from "date-fns";
import { SET_START_DATE, SET_END_DATE } from "redux/actions/user";
import { RESET } from "redux/actions/ui";
import { CLEAR_USER } from "redux/actions/auth";

const initialState = {
  startDate: null,
  endDate: null,
};

export const units = (state = initialState, action) => {
  switch (action.type) {
    case SET_START_DATE:
      return { ...state, startDate: format(action.payload.date, "yyyy-MM-dd") };
    case SET_END_DATE:
      return { ...state, endDate: format(action.payload.date, "yyyy-MM-dd") };
    case CLEAR_USER:
    case RESET:
      return initialState;
    default:
      return state;
  }
};

export default units;
