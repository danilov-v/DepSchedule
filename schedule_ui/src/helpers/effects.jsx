import { useEffect, useState } from "react";
import { getUnits, getUnitsTree } from "helpers/api";

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

export const useUnitsTree = () => {
  const [units, setUnits] = useState([]);
  const fetchUnitsTree = async () => {
    const data = await getUnitsTree();

    setUnits(data);
    console.log("set Units Tree");
  };

  useEffect(() => {
    fetchUnitsTree();
  }, []);

  return [units, fetchUnitsTree];
};
