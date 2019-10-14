import React from "react";
import { Home } from "components/home/home";

import { useCalendars } from "helpers/hooks/apiEffects";

// TODO: implement logic when BE will work
const getActiveCalendar = calendars => calendars && calendars[0];

export function Calendar() {
  const [calendars] = useCalendars();
  const activeCalendar = getActiveCalendar(calendars);

  if (!activeCalendar) return null;

  return <Home activeCalendar={activeCalendar} />;
}
