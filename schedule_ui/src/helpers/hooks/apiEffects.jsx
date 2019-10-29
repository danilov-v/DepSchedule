import { useEffect, useState, useCallback } from "react";
import { useAuth } from "components/auth-service/auth-service";
import {
  getUnitsTree,
  getEventTypes,
  getPeriods,
  getFinishedEvents,
  getCalendars,
} from "helpers/api";

const handleError = (error, logout) => {
  if (error.code === "403" || error.code === "401") {
    logout();
  } else {
    console.log(error);
  }
};

export const useUnitsTree = initialDateFrom => {
  const [units, setUnits] = useState([]);
  const { logout } = useAuth();
  const fetchUnitsTree = useCallback(async () => {
    try {
      const data = await getUnitsTree(initialDateFrom);

      setUnits(data);
      console.log("set Units Tree");
    } catch (error) {
      handleError(error, logout);
    }
  }, [initialDateFrom, logout]);

  useEffect(() => {
    fetchUnitsTree();
  }, [fetchUnitsTree]);

  return [units, fetchUnitsTree];
};

export const useFinishedEvents = count => {
  const [finishedEvents, setFinishedEvents] = useState([]);
  const { logout } = useAuth();
  const fetchFinishedEvents = useCallback(async () => {
    try {
      const data = await getFinishedEvents(count);

      setFinishedEvents(data);
    } catch (error) {
      handleError(error, logout);
    }
  }, [count, logout]);

  useEffect(() => {
    fetchFinishedEvents();
  }, [fetchFinishedEvents]);

  return [finishedEvents, fetchFinishedEvents];
};

export const useEventTypes = () => {
  const [eventTypes, setEventTypes] = useState([]);
  const { logout } = useAuth();

  const fetchEventTypes = useCallback(async () => {
    try {
      const data = await getEventTypes();

      setEventTypes(data);
      console.log("set Event Types");
    } catch (error) {
      handleError(error, logout);
    }
  }, [logout]);

  useEffect(() => {
    fetchEventTypes();
  }, [fetchEventTypes]);

  return [eventTypes, fetchEventTypes];
};

export const usePeriods = () => {
  const [periods, setPeriods] = useState([]);
  const { logout } = useAuth();

  const fetchPeriods = useCallback(async () => {
    try {
      const data = await getPeriods();

      setPeriods(data);
    } catch (error) {
      handleError(error, logout);
    }
  }, [logout]);

  useEffect(() => {
    fetchPeriods();
  }, [fetchPeriods]);

  return [periods, fetchPeriods];
};

export const useCalendars = () => {
  const [calendars, setCalendars] = useState([]);
  const { logout } = useAuth();
  const fetchCalendars = useCallback(async () => {
    try {
      const data = await getCalendars();

      setCalendars(data);
    } catch (error) {
      handleError(error, logout);
    }
  }, [logout]);

  useEffect(() => {
    fetchCalendars();
  }, [fetchCalendars]);

  return [calendars, fetchCalendars];
};
