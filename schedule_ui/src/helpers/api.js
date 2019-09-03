import { format } from "date-fns";
import {
  LOCALHOST_URL,
  DEFAULT_BE_PORT,
  API,
  UNITS_URL,
  EVENT_URL,
  EVENT_TYPE_URL,
  PERIODS,
} from "config/url";

const getUrl = apiPath =>
  `${LOCALHOST_URL}:${DEFAULT_BE_PORT}/${API}/${apiPath}`;

export const getUnits = async () => {
  const result = await fetch(getUrl(UNITS_URL));
  const data = await result.json();

  return data;
};

export const getUnitsTree = async dateFrom => {
  const result = await fetch(
    getUrl(`${UNITS_URL}/tree?dateFrom=${format(dateFrom, "yyyy-MM-dd")}`)
  );
  const data = await result.json();

  return data;
};

export const createUnit = async unitData => {
  const result = await fetch(getUrl(UNITS_URL), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(unitData),
  });
  const data = await result.json();

  if (result && result.status !== 200) {
    throw new Error(data);
  }

  return data;
};

export const getEventTypes = async () => {
  const result = await fetch(getUrl(EVENT_TYPE_URL));
  const data = await result.json();

  return data;
};

export const updateUnit = async unitData => {
  const result = await fetch(getUrl(`${UNITS_URL}/${unitData.unitId}`), {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(unitData),
  });
  const data = await result.json();

  if (result && result.status !== 200) {
    throw new Error(data);
  }

  return data;
};

export const deleteUnit = async unitId => {
  const result = await fetch(getUrl(`${UNITS_URL}/${unitId}`), {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (result && result.status !== 200) {
    throw new Error(result);
  }
};

export const createEventType = async eventTypeData => {
  const result = await fetch(getUrl(EVENT_TYPE_URL), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(eventTypeData),
  });
  const data = await result.json();

  if (result && result.status !== 200) {
    throw new Error(data);
  }

  return data;
};

export const updateEventType = async eventTypeData => {
  const result = await fetch(
    getUrl(`${EVENT_TYPE_URL}/${eventTypeData.typeId}`),
    {
      method: "PUT",
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

export const removeEventType = async typeId => {
  const result = await fetch(getUrl(`${EVENT_TYPE_URL}/${typeId}`), {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (result.status === 400) {
    throw new Error();
  }
};

export const createEvent = async eventData => {
  const result = await fetch(getUrl(EVENT_URL), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(eventData),
  });

  const data = await result.json();

  if (result.status === 400) {
    throw data;
  }

  return data;
};

export const updateEvent = async eventData => {
  const result = await fetch(getUrl(`${EVENT_URL}/${eventData.eventId}`), {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(eventData),
  });

  const data = await result.json();

  if (result.status === 400) {
    throw data;
  }

  return data;
};

export const removeEvent = async eventId => {
  const result = await fetch(getUrl(`${EVENT_URL}/${eventId}`), {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (result.status === 400) {
    throw new Error();
  }
};

/**
 * PERIODS API
 */

export const getPeriods = async () => {
  const result = await fetch(getUrl(PERIODS));
  const data = await result.json();

  return data;
};

export const createPeriod = async periodData => {
  const result = await fetch(getUrl(PERIODS), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(periodData),
  });

  const data = await result.json();

  if (result.status === 400) {
    throw data;
  }

  return data;
};

export const updatePeriod = async periodData => {
  const result = await fetch(getUrl(`${PERIODS}/${periodData.periodId}`), {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(periodData),
  });

  const data = await result.json();

  if (result.status === 400) {
    throw data;
  }

  return data;
};

export const removePeriod = async periodId => {
  const result = await fetch(getUrl(`${PERIODS}/${periodId}`), {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (result.status === 400) {
    throw new Error();
  }
};

/**
 * END PERIODS API
 */
