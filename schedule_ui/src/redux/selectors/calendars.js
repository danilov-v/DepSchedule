import { createSelector } from "reselect";

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
