import { useEffect, useState } from "react";
import { getUnits, getUnitsTree, getEventTypes, getPeriods } from "helpers/api";

export const useUnits = () => {
  const [units, setUnits] = useState([]);
  const fetchUnits = async () => {
    const data = await getUnits();

    setUnits(data);
    console.log("set Units");
  };

  useEffect(() => {
    fetchUnits();
  }, []);

  return [units, fetchUnits];
};

export const useUnitsTree = initialDateFrom => {
  const [units, setUnits] = useState([]);
  const fetchUnitsTree = async () => {
    const data = await getUnitsTree(initialDateFrom);

    setUnits(data);
    console.log("set Units Tree");
  };

  useEffect(() => {
    fetchUnitsTree();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialDateFrom]);

  return [units, fetchUnitsTree];
};

export const useEventTypes = () => {
  const [eventTypes, setEventTypes] = useState([]);

  const fetchEventTypes = async () => {
    const data = await getEventTypes();

    setEventTypes(data);
    console.log("set Event Types");
  };

  useEffect(() => {
    fetchEventTypes();
  }, []);

  return [eventTypes, fetchEventTypes];
};

export const usePeriods = () => {
  const [periods, setPeriods] = useState([]);

  const fetchPeriods = async () => {
    const data = await getPeriods();

    setPeriods(data);
  };

  useEffect(() => {
    fetchPeriods();
  }, []);

  return [periods, fetchPeriods];
};
