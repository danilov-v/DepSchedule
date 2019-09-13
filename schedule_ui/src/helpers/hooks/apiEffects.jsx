import { useEffect, useState, useCallback } from "react";
import { useAuth } from "components/auth-service/auth-service";
import { getUnits, getUnitsTree, getEventTypes, getPeriods } from "helpers/api";

const handleError = (error, logout) => {
  if (error.code === "403" || error.code === "401") {
    logout();
  } else {
    console.log(error);
  }
};

export const useUnits = () => {
  const [units, setUnits] = useState([]);
  const { logout } = useAuth();
  const fetchUnits = useCallback(async () => {
    try {
      const data = await getUnits();

      setUnits(data);
      console.log("set Units");
    } catch (error) {
      handleError(error, logout);
    }
  }, [logout]);

  useEffect(() => {
    fetchUnits();
  }, [fetchUnits]);

  return [units, fetchUnits];
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
