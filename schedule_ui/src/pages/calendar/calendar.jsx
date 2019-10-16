import React, { useState, useEffect } from "react";
import { useCalendars } from "helpers/hooks/apiEffects";
import { createCalendar } from "helpers/api";
import { Home } from "components/home/home";
import { CalendarList } from "components/calendar-list/calendar-list";

export const DEFAULT_CALENDAR_DATA = {
  activeCalendar:
    JSON.parse(window.localStorage.getItem("activeCalendar")) || null,
};

export function Calendar() {
  const [calendars] = useCalendars();
  const [activeCalendar, setActiveCalendar] = useState(
    DEFAULT_CALENDAR_DATA.activeCalendar
  );

  const selectCalendar = calendarId => {
    const selectedCalendar = calendars.find(
      calendar => calendar.calendarId === calendarId
    );

    setActiveCalendar(selectedCalendar);
  };

  const deselectCalendar = () => setActiveCalendar(null);

  const createNewCalendar = async calendarName => {
    const createdCalendar = await createCalendar(calendarName);

    setActiveCalendar(createdCalendar);
  };

  useEffect(() => {
    if (activeCalendar) {
      window.localStorage.setItem(
        "activeCalendar",
        JSON.stringify(activeCalendar)
      );
    }
  }, [activeCalendar]);

  if (activeCalendar) {
    return (
      <Home activeCalendar={activeCalendar} changeCalendar={deselectCalendar} />
    );
  }

  return (
    <CalendarList
      calendars={calendars}
      onCalendarSelect={selectCalendar}
      onNewCalendarCreate={createNewCalendar}
    />
  );
}
