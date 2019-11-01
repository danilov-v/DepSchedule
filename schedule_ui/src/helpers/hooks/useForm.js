import { useState, useEffect } from "react";
import { isEmpty } from "lodash";

export const useForm = (callback, initialState = {}, validate) => {
  const [values, setValues] = useState(initialState);
  const [errors, setErrors] = useState({});
  const [errorsShown, setErrorsShown] = useState(false);

  const resetForm = () => {
    setValues(initialState);
    setErrors({});
    setErrorsShown(false);
  };

  useEffect(() => {
    resetForm();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialState]);

  const onChange = newValues => {
    const newVals = { ...values, ...newValues };
    setErrors(validate(newVals));
    return setValues(newVals);
  };

  const onSubmit = e => {
    e.preventDefault();
    const errors = validate(values);
    setErrorsShown(true);

    if (isEmpty(errors)) {
      callback();
    } else {
      setErrors(errors);
    }
  };

  return {
    onChange,
    onSubmit,
    errors,
    errorsShown,
    setErrors,
    values,
    resetForm,
  };
};
