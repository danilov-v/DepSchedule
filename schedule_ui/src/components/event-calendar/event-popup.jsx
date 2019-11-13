import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  Row,
  Col,
  Button,
  Form,
  FormGroup,
  InputGroup,
  InputGroupAddon,
  Label,
  Input,
  CustomInput,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  FormFeedback,
} from "reactstrap";
import DatePicker from "react-datepicker";
import classnames from "classnames";
import { isDate, addDays, format } from "date-fns";
import { getEventFormSelector } from "redux/selectors/forms";
import { getEventTypesSelector } from "redux/selectors/event-types";
import { closeForm } from "redux/actions/forms";
import { useForm } from "helpers/hooks/useForm";
import { createEvent, updateEvent, removeEvent } from "redux/actions/event";

export const DEFAULT_EVENT_FORM_DATA = {
  unitId: null,
  dateFrom: null,
  duration: 0,
  eventId: null,
  eventTypeId: "",
  note: "",
  location: { name: "", type: "statical" },
  planned: false,
};

const validateEventForm = values => {
  let errors = {};

  if (!isDate(values.dateFrom))
    errors["dateFrom"] = "Необходимо выбрать дату начала события";

  if (!values.location.name || !values.location.name.trim())
    errors["locationName"] = "Введите название локации";

  return errors;
};

export function EventPopup() {
  const dispatch = useDispatch();
  const eventTypes = useSelector(getEventTypesSelector);
  const {
    isOpen,
    isEdit,
    error,
    formData = DEFAULT_EVENT_FORM_DATA,
  } = useSelector(getEventFormSelector);
  const close = () => dispatch(closeForm());
  const {
    onChange,
    onSubmit,
    errors,
    errorsShown,
    setErrors,
    values,
  } = useForm(submitForm, formData, validateEventForm);

  useEffect(() => {
    if (error && error.code) {
      switch (error.code) {
        case "INTERSECTION_OF_EVENTS":
          setErrors({ iventsIntersection: error.userMessage });
          break;
        default:
          return;
      }
    }
  }, [setErrors, error]);

  const onInputChange = event => {
    const { value, name } = event.target;
    onChange({ [name]: value });
  };

  const onChangeCheckbox = event => {
    const { checked, name } = event.target;
    onChange({ [name]: checked });
  };

  const onChangeEventType = event => {
    const { value } = event.target;

    if (formData.unitEventTypeDuration) {
      const duration = formData.unitEventTypeDuration
        ? formData.unitEventTypeDuration[value] || 1
        : 1;
      const dateTo = isDate(dateFrom) ? addDays(dateFrom, duration) : null;
      onChange({ eventTypeId: +value, duration, dateTo });
    } else {
      onChange({ eventTypeId: +value });
    }
  };

  const onChangeDuration = e => {
    const duration = e.target.value;
    if (duration < 0 || duration > 10000) return;

    const dateTo = isDate(dateFrom) ? addDays(dateFrom, duration) : null;

    onChange({ duration, dateTo });
  };

  const handleChangeDate = date =>
    onChange({
      dateFrom: date,
      dateTo: isDate(date) ? addDays(date, values.duration) : null,
    });

  const onChangeLocation = e => {
    onChange({
      location: { ...values.location, [e.target.name]: e.target.value },
    });
  };

  const onRemoveEvent = () =>
    dispatch(removeEvent({ eventId: values.eventId }));

  function submitForm() {
    const data = {
      ...values,
      dateTo: format(values.dateTo, "yyyy-MM-dd"),
      dateFrom: format(values.dateFrom, "yyyy-MM-dd"),
    };
    if (isEdit) {
      dispatch(updateEvent({ event: data }));
    } else {
      dispatch(createEvent({ event: data }));
    }
  }

  const {
    dateFrom,
    dateTo,
    note,
    eventTypeId,
    duration,
    location,
    planned,
  } = values;

  return (
    <Modal className="event-popup" isOpen={isOpen} toggle={close}>
      <Form className="p-3" onSubmit={onSubmit}>
        <ModalHeader toggle={close}>
          {isEdit ? "Редактирование" : "Создание"} События
        </ModalHeader>
        <ModalBody>
          <FormGroup>
            <Label for="unitTitle">Подразделение: </Label>
            <i>{" " + formData.unitTitle}</i>
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
            <Row>
              <Col className="pr-0" md="6">
                <DatePicker
                  selected={dateFrom}
                  selectsStart
                  startDate={dateFrom}
                  endDate={dateTo}
                  onChange={handleChangeDate}
                  locale="ru"
                  dateFormat="dd/MM/yyyy"
                  className={classnames("form-control", {
                    "is-invalid":
                      errors["iventsIntersection"] ||
                      (errors["dateFrom"] && errorsShown),
                  })}
                />
              </Col>
              <Col className="pl-0" md="6">
                <InputGroup>
                  <Input
                    type="number"
                    name="duration"
                    id="duration"
                    value={duration}
                    onChange={onChangeDuration}
                    invalid={errorsShown && !!errors["iventsIntersection"]}
                  />
                  <InputGroupAddon addonType="append">дн.</InputGroupAddon>
                </InputGroup>
              </Col>
            </Row>
            <span className="sub-text">
              {dateTo && format(dateTo, "'длится по' yyyy-MM-dd")}
            </span>
            <FormFeedback
              className={classnames({
                "d-block":
                  errorsShown &&
                  (errors["iventsIntersection"] ||
                    errors["dateFrom"] ||
                    errors["dateTo"]),
              })}
            >
              {errors["iventsIntersection"] ||
                errors["dateFrom"] ||
                errors["dateTo"]}
            </FormFeedback>
          </FormGroup>
          <FormGroup>
            <Label for="name">Локация</Label>
            <Input
              type="text"
              name="name"
              id="name"
              value={location.name}
              onChange={onChangeLocation}
              invalid={errorsShown && !!errors["locationName"]}
            />
            <FormFeedback>{errors["locationName"]}</FormFeedback>
          </FormGroup>
          <FormGroup>
            <Label for="locationType">Тип Локации</Label>
            <div>
              <CustomInput
                type="radio"
                id="district"
                name="type"
                className="d-inline-block mr-2"
                value="district"
                label="Район"
                checked={location.type === "district"}
                onChange={onChangeLocation}
              />
              <CustomInput
                type="radio"
                id="statical"
                name="type"
                className="d-inline-block "
                value="statical"
                checked={location.type === "statical"}
                label="Стационар"
                onChange={onChangeLocation}
              />
            </div>
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
          <FormGroup>
            <CustomInput
              type="checkbox"
              name="planned"
              id="planned"
              checked={planned}
              onChange={onChangeCheckbox}
              label="Запланировано"
            />
          </FormGroup>
        </ModalBody>
        <ModalFooter>
          <Button type="submit" color="success" className="mr-3">
            {isEdit ? "Обновить" : "Создать"}
          </Button>
          {isEdit && (
            <Button color="danger" onClick={onRemoveEvent} className="mr-3">
              Удалить
            </Button>
          )}
          <Button color="primary" onClick={close}>
            Закрыть
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
}
