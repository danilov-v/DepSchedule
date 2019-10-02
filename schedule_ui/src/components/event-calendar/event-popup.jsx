import React, { Fragment } from "react";
import PropTypes from "prop-types";
import {
  Button,
  Form,
  FormGroup,
  Label,
  Input,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  FormFeedback,
} from "reactstrap";
import DatePicker from "react-datepicker";
import classnames from "classnames";
import { omit } from "lodash";
import { setHours, isDate, addDays, format, differenceInDays } from "date-fns";
import {
  FAILED_EVENT_NOTIFICATION_DATA,
  SUCCESS_EVENT_NOTIFICATION_DATA,
} from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";
import { useForm } from "helpers/hooks/useForm";

const validateEventForm = values => {
  let errors = {};

  if (!isDate(values.dateFrom) && !isDate(values.dateTo))
    errors["dates"] = "Необходимо выбрать дату события";

  if (!isDate(values.dateFrom))
    errors["dateFrom"] = "Необходимо выбрать дату начала события";

  if (!isDate(values.dateTo))
    errors["dateTo"] = "Необходимо выбрать дату конца события";

  if (!values.note || values.note.length < 5)
    errors["note"] = "Описание должно содержать минимум 5 символов";

  return errors;
};

export function EventPopup({
  type,
  title,
  isOpen,
  toggle,
  onEventSubmit,
  onEventRemove,
  eventTypeDurations,
  eventTypes,
  defaultFormData,
}) {
  const {
    onChange,
    onSubmit,
    errors,
    errorsShown,
    setErrors,
    values,
  } = useForm(submitForm, defaultFormData, validateEventForm);

  const onInputChange = event => {
    const { value, name } = event.target;
    onChange({ [name]: value });
  };

  const onChangeEventType = event => {
    const { value } = event.target;

    if (eventTypeDurations) {
      const duration = eventTypeDurations ? eventTypeDurations[value] || 1 : 1;
      const dateTo = isDate(dateFrom) ? addDays(dateFrom, duration) : null;
      onChange({ eventTypeId: +value, dateTo });
    } else {
      onChange({ eventTypeId: +value });
    }
  };

  const handleChangeDate = dateFieldName => date =>
    onChange({ [dateFieldName]: date });

  const removeEvent = async () => {
    try {
      await onEventRemove(values.eventId);
      toggle();
    } catch (e) {
      NotificationManager.fire(FAILED_EVENT_NOTIFICATION_DATA);
    }
  };

  const handleError = ({ code, userMessage, devMessage }) => {
    NotificationManager.fire(FAILED_EVENT_NOTIFICATION_DATA);
    console.log(devMessage);

    switch (code) {
      case "INTERSECTION_OF_EVENTS":
        setErrors({ dates: userMessage });
        break;
      default:
        return;
    }
  };

  async function submitForm() {
    const duration = differenceInDays(
      setHours(dateTo, 0),
      setHours(dateFrom, 0)
    );

    const data = {
      ...omit(values, ["dateTo"]),
      dateFrom: format(values.dateFrom, "yyyy-MM-dd"),
      duration,
    };
    try {
      await onEventSubmit(data);
      toggle();
      NotificationManager.fire(SUCCESS_EVENT_NOTIFICATION_DATA);
    } catch (e) {
      handleError(e);
    }
  }

  const { dateFrom, dateTo, note, eventTypeId } = values;
  return (
    <Fragment>
      <Modal isOpen={isOpen} toggle={toggle}>
        <Form className="p-3" onSubmit={onSubmit}>
          <ModalHeader toggle={toggle}>
            {type === "create" ? "Создание" : "Редактирование"} События
          </ModalHeader>
          <ModalBody>
            <FormGroup>
              <Label for="unitTitle">Подразделение: </Label>
              <i>{" " + title}</i>
            </FormGroup>
            <FormGroup>
              <Label for="unitParent">Тип События</Label>
              <Input
                type="select"
                name="eventTypeId"
                id="eventTypeId"
                onChange={onChangeEventType}
                value={eventTypeId}
              >
                {eventTypes.map((eventType, i) => (
                  <option key={eventType.typeId + i} value={eventType.typeId}>
                    {eventType.description}
                  </option>
                ))}
              </Input>
            </FormGroup>
            <Label for="unitParent">Длительность События</Label>
            <FormGroup>
              <div className="d-flex">
                <DatePicker
                  selected={dateFrom}
                  selectsStart
                  startDate={dateFrom}
                  endDate={dateTo}
                  maxDate={dateTo}
                  onChange={handleChangeDate("dateFrom")}
                  locale="ru"
                  dateFormat="dd/MM/yyyy"
                  className={classnames("form-control", {
                    "is-invalid":
                      errors["dates"] || (errors["dateFrom"] && errorsShown),
                  })}
                />
                <DatePicker
                  selected={dateTo}
                  selectsEnd
                  startDate={dateFrom}
                  endDate={dateTo}
                  onChange={handleChangeDate("dateTo")}
                  minDate={dateFrom}
                  locale="ru"
                  dateFormat="dd/MM/yyyy"
                  className={classnames("form-control", {
                    "is-invalid":
                      errors["dates"] || (errors["dateTo"] && errorsShown),
                  })}
                />
              </div>
              <FormFeedback
                className={classnames({
                  "d-block":
                    errorsShown &&
                    (errors["dates"] || errors["dateFrom"] || errors["dateTo"]),
                })}
              >
                {errors["dates"] || errors["dateFrom"] || errors["dateTo"]}
              </FormFeedback>
            </FormGroup>
            <FormGroup>
              <Label for="note">Описание События</Label>
              <Input
                type="textarea"
                rows="6"
                name="note"
                id="note"
                value={note}
                onChange={onInputChange}
                invalid={errorsShown && !!errors["note"]}
              />
              <FormFeedback>{errors["note"]}</FormFeedback>
            </FormGroup>
          </ModalBody>
          <ModalFooter>
            <Button type="submit" color="success" className="mr-3">
              {type === "create" ? "Создать" : "Обновить"}
            </Button>
            {type === "edit" && (
              <Button color="danger" onClick={removeEvent} className="mr-3">
                Удалить
              </Button>
            )}
            <Button color="primary" onClick={toggle}>
              Закрыть
            </Button>
          </ModalFooter>
        </Form>
      </Modal>
    </Fragment>
  );
}

EventPopup.propTypes = {
  type: PropTypes.string.isRequired,
  title: PropTypes.string,
  isOpen: PropTypes.bool.isRequired,
  toggle: PropTypes.func,
  onSubmit: PropTypes.func,
  onEventRemove: PropTypes.func,
  eventTypeDurations: PropTypes.object,
  eventTypes: PropTypes.arrayOf(
    PropTypes.shape({
      color: PropTypes.string,
      description: PropTypes.string,
      typeId: PropTypes.number,
    })
  ),
  defaultFormData: PropTypes.shape({
    dateFrom: PropTypes.instanceOf(Date),
    duration: PropTypes.number,
    eventId: PropTypes.number,
    eventTypeId: "",
    note: "",
    unitId: PropTypes.number,
  }),
};
