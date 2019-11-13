import { LOGIN_REQUEST_FAILED } from "redux/actions/auth";
import { OPEN_FORM, CLOSE_FORM, ERROR_FORM } from "redux/actions/forms";

const initialState = {
  loginForm: {
    isError: false,
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
    case ERROR_FORM: {
      const { error } = action.payload;

      return {
        ...state,
        activeForm: {
          ...state.activeForm,
          error,
        },
      };
    }
    case CLOSE_FORM:
      return { ...state, activeForm: initialState.activeForm };

    case LOGIN_REQUEST_FAILED:
      return { ...state, loginForm: { isError: true, error: action.error } };

    default:
      return state;
  }
};

export default init;
