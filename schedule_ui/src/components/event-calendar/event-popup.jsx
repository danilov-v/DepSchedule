import React, { useState, useEffect, Fragment } from "react";
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
} from "reactstrap";
import DatePicker from "react-datepicker";
import classnames from "classnames";
import { omit, forIn, values, cloneDeep } from "lodash";
import { isDate, addDays, format, differenceInDays } from "date-fns";
import * as notifications from "constants/notifications";
import { NotificationManager } from "helpers/notification-manager";
import { isString } from "util";

const validationRules = {
  dateFrom: isDate,
  dateTo: isDate,
  note: text => isString(text) && text.length > 5,
};

const defaultValidation = {
  dateFrom: false,
  dateTo: false,
  note: false,
};

export function EventPopup({
  type,
  title,
  isOpen,
  toggle,
  onSubmit,
  onEventRemove,
  eventTypeDurations,
  eventTypes,
  defaultFormData,
}) {
  const [validState, setValidState] = useState(defaultValidation);
  const [isValidationShown, showValidation] = useState(false);
  const [formData, setData] = useState(defaultFormData);

  useEffect(() => {
    const duration =
      defaultFormData.duration ||
      (eventTypeDurations
        ? eventTypeDurations[defaultFormData.eventTypeId] || 1
        : 1);

    const newFormData = {
      ...defaultFormData,
      duration,
      dateTo: isDate(defaultFormData.dateFrom)
        ? addDays(defaultFormData.dateFrom, duration)
        : null,
    };

    setData(newFormData);
    validate(newFormData);
    showValidation(false);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [defaultFormData, eventTypeDurations]);

  const validate = formData => {
    const _validState = cloneDeep(validState);

    forIn(_validState, (value, key) => {
      _validState[key] = validationRules[key](formData[key]);
    });
    setValidState(_validState);
  };

  const validateAll = formData => {
    const isAllValid = values(validState).reduce(
      (isAllValid, isValid) => isAllValid && isValid,
      true
    );
    showValidation(!isAllValid);
    return isAllValid;
  };

  const onInputChange = event => {
    const { value, name } = event.target;
    const newFormData = { ...formData, [name]: value };

    validate(newFormData);
    setData(newFormData);
  };

  const onChangeEventType = event => {
    const { value } = event.target;
    let newFormData;
    if (eventTypeDurations) {
      const duration = eventTypeDurations ? eventTypeDurations[value] || 1 : 1;
      const dateTo = isDate(dateFrom) ? addDays(dateFrom, duration) : null;

      newFormData = { ...formData, eventTypeId: +value, dateTo };
    } else {
      newFormData = { ...formData, eventTypeId: +value };
    }

    validate(newFormData);
    setData(newFormData);
  };

  const handleChangeDate = dateFieldName => date => {
    const newFormData = { ...formData, [dateFieldName]: date };

    validate(newFormData);
    setData(newFormData);
  };

  const submitForm = async event => {
    event.preventDefault();

    if (validateAll()) {
      const duration = differenceInDays(dateTo, dateFrom);

      const data = {
        ...omit(formData, ["dateTo"]),
        dateFrom: format(formData.dateFrom, "yyyy-MM-dd"),
        duration,
      };
      try {
        await onSubmit(data);
        toggle();

        NotificationManager.fire(
          notifications["SUCCESS_EVENT_NOTIFICATION_DATA"]
        );
      } catch (e) {
        NotificationManager.fire(
          notifications[e.message] ||
            notifications["FAILED_EVENT_NOTIFICATION_DATA"]
        );
      }
    } else {
      NotificationManager.fire(notifications["FAILED_EVENT_NOTIFICATION_DATA"]);
    }

    return false;
  };

  const removeEvent = async () => {
    try {
      await onEventRemove(formData.eventId);
      toggle();
    } catch (e) {
      console.log(e.message);
      NotificationManager.fire(
        notifications[e.message] ||
          notifications["FAILED_EVENT_NOTIFICATION_DATA"]
      );
    }
  };

  const { dateFrom, dateTo, note, eventTypeId } = formData;

  return (
    <Fragment>
      <Modal isOpen={isOpen} toggle={toggle}>
        <Form className="p-3" onSubmit={submitForm}>
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
            <FormGroup className="d-flex">
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
                  "is-invalid": !validState["dateFrom"] && isValidationShown,
                })}
              />
              <DatePicker
                selected={dateTo}
                selectsEnd
                startDate={dateTo}
                endDate={dateTo}
                onChange={handleChangeDate("dateTo")}
                minDate={dateFrom}
                locale="ru"
                dateFormat="dd/MM/yyyy"
                className={classnames("form-control", {
                  "is-invalid": isValidationShown && !validState["dateTo"],
                })}
              />
            </FormGroup>
            <FormGroup>
              <Label for="note">Описание События</Label>
              <Input
                type="textarea"
                name="note"
                id="note"
                value={note}
                onChange={onInputChange}
                invalid={isValidationShown && !validState["note"]}
              />
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
