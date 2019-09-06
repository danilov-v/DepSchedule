import { useEffect, useState, useCallback } from "react";
import { getUnits, getUnitsTree, getEventTypes, getPeriods } from "helpers/api";

export const useUnits = () => {
  const [units, setUnits] = useState([]);
  const fetchUnits = useCallback(async () => {
    const data = await getUnits();

    setUnits(data);
    console.log("set Units");
  }, []);

  useEffect(() => {
    fetchUnits();
  }, [fetchUnits]);

  return [units, fetchUnits];
};

export const useUnitsTree = initialDateFrom => {
  const [units, setUnits] = useState([]);
  const fetchUnitsTree = useCallback(async () => {
    const data = await getUnitsTree(initialDateFrom);

    setUnits(data);
    console.log("set Units Tree");
  }, [initialDateFrom]);

  useEffect(() => {
    fetchUnitsTree();
  }, [fetchUnitsTree]);

  return [units, fetchUnitsTree];
};

export const useEventTypes = () => {
  const [eventTypes, setEventTypes] = useState([]);

  const fetchEventTypes = useCallback(async () => {
    const data = await getEventTypes();

    setEventTypes(data);
    console.log("set Event Types");
  }, []);

  useEffect(() => {
    fetchEventTypes();
  }, [fetchEventTypes]);

  return [eventTypes, fetchEventTypes];
};

export const usePeriods = () => {
  const [periods, setPeriods] = useState([]);

  const fetchPeriods = useCallback(async () => {
    const data = await getPeriods();

    setPeriods(data);
  }, []);

  useEffect(() => {
    fetchPeriods();
  }, [fetchPeriods]);

  return [periods, fetchPeriods];
};
