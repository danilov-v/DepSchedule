import { createSelector } from "reselect";
import { getActiveCalendarInfo } from "redux/selectors/calendars";
import { getDayWithoutMinutes } from "utils/date";

export const getUserSelector = state => state.user;

export const getUserDataSelector = createSelector(
  getUserSelector,
  getActiveCalendarInfo,
  (user, activeCalendar) => {
    const startDate = user.startDate
      ? new Date(user.startDate)
      : activeCalendar.startDate;
    const endDate = user.endDate
      ? new Date(user.endDate)
      : activeCalendar.endDate;

    return {
      startDate: getDayWithoutMinutes(startDate),
      endDate: getDayWithoutMinutes(endDate),
    };
  }
);
