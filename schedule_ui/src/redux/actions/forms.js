export const OPEN_FORM = "OPEN_FORM";
export const CLOSE_FORM = "CLOSE_FORM";
export const ERROR_FORM = "ERROR_FORM";

export const openForm = ({ formName, formData, isEdit }) => ({
  type: OPEN_FORM,
  payload: {
    formName,
    formData,
    isEdit,
  },
});

export const closeForm = () => ({ type: CLOSE_FORM });

export const setErrorForm = ({ error }) => ({
  type: ERROR_FORM,
  payload: {
    error,
  },
});
