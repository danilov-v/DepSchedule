import React from "react";
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
import { NotificationManager } from "helpers/notification-manager";
import {
  SUCCESS_PERIOD_NOTIFICATION_DATA,
  FAILED_PERIOD_NOTIFICATION_DATA,
} from "constants/notifications";

const validatePeriodForm = values => {
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
  type,
  onPeriodSubmit,
  defaultFormData,
}) {
  const {
    onChange,
    onSubmit,
    errors,
    errorsShown,
    setErrors,
    values,
  } = useForm(submitForm, defaultFormData, validatePeriodForm);

  const onInputChange = ({ target }) =>
    onChange({ [target.name]: target.value });

  const handleChangeDate = fieldName => date => onChange({ [fieldName]: date });

  const handleError = ({ code, userMessage, devMessage }) => {
    NotificationManager.fire(FAILED_PERIOD_NOTIFICATION_DATA);
    console.log(devMessage);

    switch (code) {
      case "INTERSECTION_OF_PERIODS":
        setErrors({ dates: userMessage });
        break;
      default:
        return;
    }
  };

  async function submitForm() {
    try {
      await onPeriodSubmit(values);
      toggle();

      NotificationManager.fire(SUCCESS_PERIOD_NOTIFICATION_DATA);
    } catch (e) {
      handleError(e);
    }
  }
  const { name, startDate, endDate } = values;

  return (
    <Modal isOpen={isOpen} toggle={toggle}>
      <Form className="p-3">
        <ModalHeader toggle={toggle}>
          {type === "create" ? "Создание" : "Редакитрование"} Периоды
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
                placeholderText="15/09/2019"
                className={classnames("form-control", {
                  "is-invalid":
                    errorsShown && (errors["dates"] || errors["startDate"]),
                })}
              />
              <DatePicker
                selected={endDate}
                selectsEnd
                startDate={endDate}
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
            {type === "create" ? "Создать" : "Обновить"}
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
  type: PropTypes.string,
  onPeriodSubmit: PropTypes.func,
  defaultFormData: PropTypes.shape({
    name: PropTypes.string,
    startDate: PropTypes.instanceOf(Date),
    endDate: PropTypes.instanceOf(Date),
  }),
};

PeriodsPopup.defaultProps = {
  isOpen: false,
};
