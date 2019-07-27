import { LOCALHOST_URL, DEFAULT_BE_PORT, UNITS_URL } from "config/url";

export const getUnits = async () => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${UNITS_URL}/tree`
  );
  const data = await result.json();

  return data;
};
