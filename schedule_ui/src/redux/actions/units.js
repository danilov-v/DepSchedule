// UNITS
export const FETCH_UNITS = "FETCH_UNITS";
export const CREATE_UNIT = "CREATE_UNIT";
export const UPDATE_UNIT = "UPDATE_UNIT";
export const REMOVE_UNIT = "REMOVE_UNIT";
export const UPDATE_UNITS = "UPDATE_UNITS";

export const fetchUnits = payload => ({
  type: FETCH_UNITS,
  payload,
});

export const createUnit = payload => ({
  type: CREATE_UNIT,
  payload,
});

export const updateUnit = payload => ({
  type: UPDATE_UNIT,
  payload,
});

export const removeUnit = payload => ({
  type: REMOVE_UNIT,
  payload,
});

export const updateUnits = payload => ({
  type: UPDATE_UNITS,
  payload,
});
