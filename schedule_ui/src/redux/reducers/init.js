import { INIT_ROUTE_DONE } from "redux/actions/init";

const initialState = {
  routeInit: false,
};

export const init = (state = initialState, action) => {
  switch (action.type) {
    case INIT_ROUTE_DONE:
      return { ...state, routeInit: true };
    default:
      return state;
  }
};

export default init;
