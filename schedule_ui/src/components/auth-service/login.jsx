import React from "react";
import PropTypes from "prop-types";
import { Button, Form, FormGroup, FormFeedback, Input } from "reactstrap";
import { useForm } from "helpers/hooks/useForm";

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

export const Login = ({ login }) => {
  const onFormSubmit = async () => {
    try {
      await login(values);
    } catch (e) {
      console.log(e);
      setErrors({ auth: "Неверное имя или пароль" });
    }
  };

  const {
    onChange,
    onSubmit,
    errors,
    errorsShown,
    setErrors,
    values,
  } = useForm(onFormSubmit, defaultFormData, validateForm);

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

Login.propTypes = {
  login: PropTypes.func,
};
