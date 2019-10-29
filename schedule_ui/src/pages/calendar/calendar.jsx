import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchCalendars,
  createCalendar,
  setActiveCalendar,
  removeCalendar,
} from "redux/actions/calendars";
import { getCalendarsSelector } from "redux/selectors/calendars";
import { CALENDAR_CONFIRMATION_OPTIONS } from "constants/confirmations";
import { FAILED_CALENDAR_NOTIFICATION_DATA } from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";
import { useConfirmation } from "components/confirmation-service/confirmation-service";
import { CalendarList } from "components/calendar-list/calendar-list";

export function Calendar() {
  const confirm = useConfirmation();
  const dispatch = useDispatch();
  const calendars = useSelector(getCalendarsSelector);

  useEffect(() => {
    dispatch(fetchCalendars());
  }, [dispatch]);

  const createNewCalendar = calendar => dispatch(createCalendar({ calendar }));

  const removeCalendarHandler = calendarId => {
    confirm(CALENDAR_CONFIRMATION_OPTIONS).then(() => {
      try {
        dispatch(removeCalendar({ calendarId }));
      } catch (e) {
        NotificationManager.fire(FAILED_CALENDAR_NOTIFICATION_DATA);
      }
    });
  };

  const selectCalendar = calendarId =>
    dispatch(setActiveCalendar({ calendarId }));

  return (
    <CalendarList
      calendars={calendars}
      onCalendarSelect={selectCalendar}
      onNewCalendarCreate={createNewCalendar}
      onCalendarRemove={removeCalendarHandler}
    />
  );
}
