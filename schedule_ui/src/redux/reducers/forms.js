import { LOGIN_REQUEST_FAILED } from "redux/actions/auth";
import {
  OPEN_PERIOD_FORM,
  CLOSE_PERIOD_FORM,
  EVENT_REQUEST_FAILED,
  PERIOD_REQUEST_FAILED,
  OPEN_EVENT_FORM,
  CLOSE_EVENT_FORM,
  OPEN_EVENT_TYPE_FORM,
  CLOSE_EVENT_TYPE_FORM,
  EVENT_TYPE_REQUEST_FAILED,
  OPEN_FORM,
  CLOSE_FORM,
} from "redux/actions/forms";
import {
  DEFAULT_PERIOD_FORM_DATA,
  DEFAULT_EVENT_FORM_DATA,
  DEFAULT_EVENT_TYPE_FORM_DATA,
} from "constants/forms";

const initialState = {
  loginForm: {
    isError: false,
    error: null,
  },
  periodForm: {
    isOpen: false,
    isEdit: false,
    initialFormData: DEFAULT_PERIOD_FORM_DATA,
    error: null,
  },
  eventForm: {
    isOpen: false,
    isEdit: false,
    formData: DEFAULT_EVENT_FORM_DATA,
    error: null,
  },
  eventTypeForm: {
    isOpen: false,
    isEdit: false,
    initialFormData: DEFAULT_EVENT_TYPE_FORM_DATA,
    error: null,
  },
  activeForm: {
    formName: "",
    isOpen: false,
    isEdit: false,
    formData: {},
    error: null,
  },
};

export const init = (state = initialState, action) => {
  switch (action.type) {
    case OPEN_FORM: {
      const { formName, formData, isEdit = false } = action.payload;
      return {
        ...state,
        activeForm: { formName, formData, isEdit, isOpen: true, error: null },
      };
    }
    case CLOSE_FORM:
      return { ...state, activeForm: initialState.activeForm };

    case LOGIN_REQUEST_FAILED:
      return { ...state, loginForm: { isError: true, error: action.error } };

    case OPEN_PERIOD_FORM:
      return {
        ...state,
        periodForm: {
          ...action.payload,
          isOpen: true,
          error: null,
          initialFormData: action.payload.initialFormData || {
            ...DEFAULT_PERIOD_FORM_DATA,
          },
        },
      };

    case CLOSE_PERIOD_FORM:
      return {
        ...state,
        periodForm: initialState.periodForm,
      };

    case PERIOD_REQUEST_FAILED:
      return {
        ...state,
        periodForm: { ...state.periodForm, error: action.payload.error },
      };

    case OPEN_EVENT_FORM:
      const { isEdit = false, formData } = action.payload;
      return {
        ...state,
        eventForm: {
          ...state.eventForm,
          formData: {
            ...initialState.eventForm.formData,
            ...formData,
          },
          isOpen: true,
          isEdit,
        },
      };
    case CLOSE_EVENT_FORM:
      return {
        ...state,
        eventForm: {
          ...initialState.eventForm,
        },
      };
    case EVENT_REQUEST_FAILED:
      const { error } = action.payload;

      return {
        ...state,
        eventForm: {
          ...state.eventForm,
          error,
        },
      };

    case OPEN_EVENT_TYPE_FORM:
      return {
        ...state,
        eventTypeForm: {
          ...action.payload,
          isOpen: true,
          error: null,
          initialFormData: action.payload.initialFormData || {
            ...DEFAULT_EVENT_TYPE_FORM_DATA,
          },
        },
      };
    case CLOSE_EVENT_TYPE_FORM:
      return {
        ...state,
        eventTypeForm: initialState.eventTypeForm,
      };
    case EVENT_TYPE_REQUEST_FAILED:
      return {
        ...state,
        eventTypeForm: {
          ...state.eventTypeForm,
          ...action.payload,
        },
      };
    default:
      return state;
  }
};

export default init;
