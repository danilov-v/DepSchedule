import { createSelector } from "reselect";
import { getDayWithoutMinutes } from "utils/date";

export const getPeriodsSelector = state => state.periods.periods;

export const getFormattedPeriodsSelector = createSelector(
  getPeriodsSelector,
  periods =>
    periods.map(period => ({
      ...period,
      startDate: getDayWithoutMinutes(new Date(period.startDate)),
      endDate: getDayWithoutMinutes(new Date(period.endDate)),
    }))
);
