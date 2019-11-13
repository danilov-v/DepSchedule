// PERIOD FORM
export const OPEN_PERIOD_FORM = "OPEN_PERIOD_FORM";
export const CLOSE_PERIOD_FORM = "CLOSE_PERIOD_FORM";
export const PERIOD_REQUEST_FAILED = "PERIOD_REQUEST_FAILED";
// EVENT FORM
export const OPEN_EVENT_FORM = "OPEN_EVENT_FORM";
export const CLOSE_EVENT_FORM = "CLOSE_EVENT_FORM";
export const EVENT_REQUEST_FAILED = "EVENT_REQUEST_FAILED";
// EVENT TYPE FORM
export const OPEN_EVENT_TYPE_FORM = "OPEN_EVENT_TYPE_FORM";
export const CLOSE_EVENT_TYPE_FORM = "CLOSE_EVENT_TYPE_FORM";
export const EVENT_TYPE_REQUEST_FAILED = "EVENT_TYPE_REQUEST_FAILED";

export const OPEN_FORM = "OPEN_FORM";
export const CLOSE_FORM = "CLOSE_FORM";

export const openForm = ({ formName, formData, isEdit }) => ({
  type: OPEN_FORM,
  payload: {
    formName,
    formData,
    isEdit,
  },
});

export const closeForm = () => ({ type: CLOSE_FORM });

export const openPeriodForm = (isEdit, initialFormData) => ({
  type: OPEN_PERIOD_FORM,
  payload: {
    isEdit,
    initialFormData,
  },
});

export const closePeriodForm = () => ({
  type: CLOSE_PERIOD_FORM,
});

export const periodRequestFail = error => ({
  type: PERIOD_REQUEST_FAILED,
  payload: {
    error,
  },
});

export const openEventForm = payload => ({
  type: OPEN_EVENT_FORM,
  payload,
});

export const closeEventForm = () => ({ type: CLOSE_EVENT_FORM });

export const getFailedEventAction = payload => ({
  type: EVENT_REQUEST_FAILED,
  payload,
});

export const openEventTypeForm = payload => ({
  type: OPEN_EVENT_TYPE_FORM,
  payload,
});

export const closeEventTypeForm = () => ({
  type: CLOSE_EVENT_TYPE_FORM,
});

export const eventTypeRequestFail = payload => ({
  type: EVENT_TYPE_REQUEST_FAILED,
  payload,
});
