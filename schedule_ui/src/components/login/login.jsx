import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Button, Form, FormGroup, FormFeedback, Input } from "reactstrap";
import { useForm } from "helpers/hooks/useForm";
import { loginRequest } from "redux/actions/auth";
import { getLoginErrorSelector } from "redux/selectors/ui";

import "./login.scss";

const defaultFormData = {
  login: "",
  password: "",
};

const validateForm = values => {
  let errors = {};

  if (!values.login || values.login.trim().length < 3)
    errors["login"] = "Имя пользовтеля должно содержать минимум 3 символа";

  if (!values.password || values.password.trim().length < 5)
    errors["password"] = "Пароль должен содержать минимум 5 символов";

  return errors;
};

export const Login = () => {
  const dispatch = useDispatch();
  const loginDataError = useSelector(getLoginErrorSelector);
  const onFormSubmit = () => dispatch(loginRequest({ user: values }));
  const {
    onChange,
    onSubmit,
    errors,
    errorsShown,
    setErrors,
    values,
  } = useForm(onFormSubmit, defaultFormData, validateForm);

  useEffect(() => {
    if (loginDataError.isError) {
      setErrors({ auth: "Неверное имя или пароль" });
    }
  }, [setErrors, loginDataError]);

  const handleInputChange = ({ target }) =>
    onChange({ [target["name"]]: target.value });

  return (
    <div className="h-100 w-100 text-dark emblem-background">
      <Form className="login-form">
        <h2 className="text-center mb-5">Вход в Cистему</h2>
        <FormFeedback>{errors["auth"]}</FormFeedback>
        <FormGroup>
          <Input
            type="text"
            rows="6"
            name="login"
            placeholder="Имя пользователя"
            id="note"
            value={values.login}
            onChange={handleInputChange}
            invalid={errorsShown && (!!errors["auth"] || !!errors["login"])}
          />
          <FormFeedback>{errors["login"]}</FormFeedback>
        </FormGroup>
        <FormGroup>
          <Input
            type="password"
            rows="6"
            name="password"
            placeholder="Пароль"
            id="password"
            value={values.password}
            onChange={handleInputChange}
            invalid={errorsShown && (!!errors["auth"] || !!errors["password"])}
          />
          <FormFeedback>{errors["auth"] || errors["password"]}</FormFeedback>
        </FormGroup>
        <Button type="submit" color="primary" block onClick={onSubmit}>
          Войти
        </Button>
      </Form>
    </div>
  );
};
