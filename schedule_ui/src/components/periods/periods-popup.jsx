import React, { useEffect } from "react";
import PropTypes from "prop-types";
import {
  Button,
  Form,
  FormGroup,
  FormFeedback,
  Label,
  Input,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "reactstrap";
import DatePicker from "react-datepicker";
import classnames from "classnames";
import { isDate } from "date-fns";
import { useForm } from "helpers/hooks/useForm";

export const validatePeriodForm = values => {
  let errors = {};

  if (!isDate(values.startDate) && !isDate(values.endDate))
    errors["dates"] = "Необходимо выбрать дату периода";

  if (!isDate(values.startDate))
    errors["startDate"] = "Необходимо выбрать дату начала периода";

  if (!isDate(values.endDate))
    errors["endDate"] = "Необходимо выбрать дату конца периода";

  if (!values.name || !values.name.trim())
    errors["name"] = "Назвние должно содержать минимум 1 символ";

  return errors;
};

export function PeriodsPopup({
  isOpen,
  toggle,
  isEdit,
  onPeriodSubmit,
  defaultFormData,
  error,
}) {
  const {
    onChange,
    onSubmit,
    errors,
    errorsShown,
    setErrors,
    values,
  } = useForm(submitForm, defaultFormData, validatePeriodForm);

  useEffect(() => {
    if (error) {
      const { code, userMessage } = error;
      switch (code) {
        case "DATES_INTERSECTION":
          setErrors({ dates: userMessage });
          break;
        default:
          return;
      }
    }
  }, [error, setErrors]);

  const onInputChange = ({ target }) =>
    onChange({ [target.name]: target.value });

  const handleChangeDate = fieldName => date => onChange({ [fieldName]: date });

  function submitForm() {
    onPeriodSubmit(values);
  }

  const { name, startDate, endDate } = values;

  return (
    <Modal isOpen={isOpen} toggle={toggle}>
      <Form className="p-3">
        <ModalHeader toggle={toggle}>
          {!isEdit ? "Создание" : "Редакитрование"} Периода
        </ModalHeader>
        <ModalBody>
          <FormGroup>
            <Label for="description">Имя Периода</Label>
            <Input
              type="text"
              rows="6"
              name="name"
              placeholder="Период..."
              value={name}
              onChange={onInputChange}
              invalid={!!errors["name"] && errorsShown}
            />
            <FormFeedback>{errors["name"]}</FormFeedback>
          </FormGroup>
          <Label for="unitParent">Длительность События</Label>
          <FormGroup>
            <div className="d-flex">
              <DatePicker
                selected={startDate}
                selectsStart
                startDate={startDate}
                endDate={endDate}
                maxDate={endDate}
                onChange={handleChangeDate("startDate")}
                locale="ru"
                dateFormat="dd/MM/yyyy"
                placeholderText="13/04/1995"
                className={classnames("form-control", {
                  "is-invalid":
                    errorsShown && (errors["dates"] || errors["startDate"]),
                })}
              />
              <DatePicker
                selected={endDate}
                selectsEnd
                startDate={startDate}
                endDate={endDate}
                onChange={handleChangeDate("endDate")}
                minDate={startDate}
                locale="ru"
                dateFormat="dd/MM/yyyy"
                placeholderText="04/12/1995"
                className={classnames("form-control", {
                  "is-invalid":
                    errorsShown && (errors["dates"] || errors["endDate"]),
                })}
              />
            </div>
            <FormFeedback
              className={classnames({
                "d-block":
                  errorsShown &&
                  (errors["dates"] || errors["startDate"] || errors["endDate"]),
              })}
            >
              {errors["dates"] || errors["startDate"] || errors["endDate"]}
            </FormFeedback>
          </FormGroup>
        </ModalBody>
        <ModalFooter>
          <Button
            type="submit"
            color="success"
            className="mr-3"
            onClick={onSubmit}
          >
            {!isEdit ? "Создать" : "Обновить"}
          </Button>
          <Button color="primary" onClick={toggle}>
            Закрыть
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
}

PeriodsPopup.propTypes = {
  toggle: PropTypes.func.isRequired,
  isOpen: PropTypes.bool,
  isEdit: PropTypes.bool,
  onPeriodSubmit: PropTypes.func,
  error: PropTypes.object,
  defaultFormData: PropTypes.shape({
    name: PropTypes.string,
    startDate: PropTypes.instanceOf(Date),
    endDate: PropTypes.instanceOf(Date),
  }),
};

PeriodsPopup.defaultProps = {
  isOpen: false,
};
