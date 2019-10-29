export const INIT_ROUTE = "INIT_ROUTE";
export const INIT_ROUTE_DONE = "INIT_ROUTE_DONE";

export const initRoute = payload => ({
  type: INIT_ROUTE,
  payload,
});

export const routeInitialized = () => ({
  type: INIT_ROUTE_DONE,
});
