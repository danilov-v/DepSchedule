import { format } from "date-fns";
import {
  LOCALHOST_URL,
  DEFAULT_BE_PORT,
  UNITS_URL,
  EVENT_TYPE_URL,
} from "config/url";

export const getUnits = async () => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${UNITS_URL}`
  );
  const data = await result.json();

  return data;
};

export const getUnitsTree = async dateFrom => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${UNITS_URL}/tree?dateFrom=${format(
      dateFrom,
      "yyyy-MM-dd"
    )}`
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
  const data = await result.json();

  if (result && result.status !== 200) {
    throw new Error(data);
  }

  return data;
};

export const updateUnit = async unitData => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${UNITS_URL}/${unitData.unitId}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(unitData),
    }
  );
  const data = await result.json();

  if (result && result.status !== 200) {
    throw new Error(data);
  }

  return data;
};

export const deleteUnit = async unitId => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${UNITS_URL}/${unitId}`,
    {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }
  );

  if (result && result.status !== 200) {
    throw new Error(result);
  }
};

export const createEventType = async eventTypeData => {
  const result = await fetch(
    `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${EVENT_TYPE_URL}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(eventTypeData),
    }
  );
  const data = await result.json();

  if (result && result.status !== 200) {
    throw new Error(data);
  }

  return data;
};
