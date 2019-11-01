import { UPDATE_UNITS } from "redux/actions/units";
import { RESET } from "redux/actions/ui";
import { CLEAR_USER } from "redux/actions/auth";

const initialState = {
  units: [],
};

export const units = (state = initialState, action) => {
  switch (action.type) {
    case UPDATE_UNITS:
      return { ...state, units: [...action.payload.units] };

    case CLEAR_USER:
    case RESET:
      return initialState;
    default:
      return state;
  }
};

export default units;
