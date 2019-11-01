import { createSelector } from "reselect";
import { addDays } from "date-fns";
import { getDayWithoutMinutes } from "utils/date";

export const getCalendarsSelector = state => state.calendars.calendars;

export const getActiveCalendarSelector = state =>
  state.calendars.activeCalendarId;

export const getActiveCalendar = createSelector(
  [getCalendarsSelector, getActiveCalendarSelector],
  (calendars, activeCalendarId) => {
    const activeCalendar = calendars.find(
      calendar => calendar.calendarId === activeCalendarId
    );

    return activeCalendar;
  }
);

export const getActiveCalendarInfo = createSelector(
  getActiveCalendar,
  calendar => {
    const {
      calendarId,
      name,
      shift,
      dateTo,
      dateFrom,
      isAstronomical,
    } = calendar;
    return {
      calendarId,
      name,
      shift,
      isAstronomical,
      startDate: getDayWithoutMinutes(new Date(dateFrom)),
      endDate: getDayWithoutMinutes(new Date(dateTo)),
      operationalDate: getDayWithoutMinutes(addDays(new Date(dateFrom), shift)),
    };
  }
);
