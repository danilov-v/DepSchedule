import { UPDATE_USER, CLEAR_USER } from "redux/actions/auth";

const initialState = {
  login: null,
  role: null,
  token: null,
};

export const auth = (state = initialState, action) => {
  switch (action.type) {
    case UPDATE_USER:
      return {
        ...state,
        ...action.payload,
      };
    case CLEAR_USER:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

export default auth;
