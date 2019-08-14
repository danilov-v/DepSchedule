import { useState, useEffect } from "react";
import { isEmpty } from "lodash";

export const useForm = (callback, initialState = {}, validate) => {
  const [values, setValues] = useState(initialState);
  const [errors, setErrors] = useState({});
  const [errorsShown, setErrorsShown] = useState(false);

  useEffect(() => {
    //RESET FORM WIHT NEW INITIAL STATE
    setValues(initialState);
    setErrors({});
    setErrorsShown(false);
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
  };
};
