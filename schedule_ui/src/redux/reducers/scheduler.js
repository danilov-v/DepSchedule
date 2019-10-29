import { addDays, differenceInMonths } from "date-fns";
import {
  UPDATE_START_DATE,
  UPDATE_END_DATE,
  UPDATE_OPERATIONAL_DATE,
  UPDATE_UNITS,
  UPDATE_PERIODS,
  UPDATE_EVENTS,
  RESET_SCHEDULER,
} from "redux/actions/scheduler";
import { CLEAR_USER } from "redux/actions/auth";
import { getDayWithoutMinutes } from "utils/date";
import { FOUR_MONTH } from "constants/calendar";

const astronomicalDate = getDayWithoutMinutes(new Date());

const initialState = {
  startDate: astronomicalDate,
  endDate: addDays(astronomicalDate, FOUR_MONTH),
  operationalDate: astronomicalDate,
  units: [],
  periods: [],
  events: [],
};

export const scheduler = (state = initialState, action) => {
  switch (action.type) {
    case UPDATE_START_DATE:
      return { ...state, startDate: getDayWithoutMinutes(action.payload.date) };
    case UPDATE_END_DATE:
      const endDate =
        differenceInMonths(action.payload.date, state.startDate) > 4
          ? action.payload.date
          : addDays(state.startDate, FOUR_MONTH);

      return { ...state, endDate };
    case UPDATE_OPERATIONAL_DATE:
      return {
        ...state,
        operationalDate: getDayWithoutMinutes(action.payload.date),
      };
    case UPDATE_UNITS:
      return { ...state, units: [...action.payload.units] };
    case UPDATE_PERIODS:
      return { ...state, periods: [...action.payload.periods] };

    case UPDATE_EVENTS:
      return { ...state, events: [...action.payload.events] };

    case CLEAR_USER:
    case RESET_SCHEDULER:
      return initialState;
    default:
      return state;
  }
};

export default scheduler;
