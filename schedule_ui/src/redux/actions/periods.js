// PERIODS
export const FETCH_PERIODS = "FETCH_PERIODS";
export const CREATE_PERIOD = "CREATE_PERIOD";
export const EDIT_PERIOD = "EDIT_PERIOD";
export const REMOVE_PERIOD = "REMOVE_PERIOD";
export const UPDATE_PERIODS = "UPDATE_PERIODS";

export const fetchPeriods = () => ({
  type: FETCH_PERIODS,
});

export const updatePeriods = periods => ({
  type: UPDATE_PERIODS,
  payload: {
    periods,
  },
});

export const createPeriod = period => ({
  type: CREATE_PERIOD,
  payload: {
    period,
  },
});

export const updatePeriod = period => ({
  type: EDIT_PERIOD,
  payload: {
    period,
  },
});

export const removePeriod = periodId => ({
  type: REMOVE_PERIOD,
  payload: {
    periodId,
  },
});
