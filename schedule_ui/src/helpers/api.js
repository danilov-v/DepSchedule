import { LOCALHOST_URL, DEFAULT_BE_PORT, UNITS_URL } from "config/url";

export const getUnits = async () => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${UNITS_URL}`
  );
  const data = await result.json();

  return data;
};

export const getUnitsTree = async () => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${UNITS_URL}/tree`
  );
  const data = await result.json();

  return data;
};

export const createUnit = async unitData => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${UNITS_URL}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(unitData),
    }
  );
  const data = result.json();
  return data;
};
