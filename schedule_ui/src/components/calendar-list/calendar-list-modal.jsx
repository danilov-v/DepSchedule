import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Input,
  FormGroup,
  Label,
  Row,
  Col,
  FormFeedback,
} from "reactstrap";
import { isDate, isBefore, format } from "date-fns";
import DatePicker from "react-datepicker";
import { useForm } from "helpers/hooks/useForm";

const formData = {
  calendarName: "",
  dateFrom: new Date("2019-09-01"),
  dateTo: new Date("2020-01-01"),
  isAstronomical: true,
};

const validate = values => {
  const errors = {};

  if (!isDate(values.dateFrom))
    errors["dateFrom"] = "Необходимо выбрать дату начала календаря";

  if (!isDate(values.dateTo))
    errors["dateTo"] = "Необходимо выбрать дату конца календаря";

  if (isBefore(values.dateTo, values.dateFrom))
    errors["range"] = "Дата начала должна быть переред датой конца";

  if (!values.calendarName || values.calendarName.length < 4)
    errors["calendarName"] =
      "Название календаря должно содержать минимум 4 символов";

  return errors;
};

export const CalendarListModal = ({ modal, onToggle, onNewCalendarCreate }) => {
  const {
    onChange,
    onSubmit,
    errors,
    errorsShown,
    resetForm,
    values,
  } = useForm(
    () => {
      const { calendarName: name, dateTo, dateFrom, isAstronomical } = values;

      onNewCalendarCreate({
        name,
        shift: 0,
        dateTo: format(dateTo, "yyyy-MM-dd"),
        dateFrom: format(dateFrom, "yyyy-MM-dd"),
        isAstronomical,
      });
    },
    formData,
    validate
  );
  const handleChange = event => onChange({ calendarName: event.target.value });
  const handleCheckboxChange = event =>
    onChange({ isAstronomical: event.target.checked });
  const handleStartDate = date => onChange({ dateFrom: date });
  const handleEndDate = date => onChange({ dateTo: date });
  const toggle = () => {
    resetForm();
    onToggle();
  };
  const { dateFrom, dateTo, calendarName, isAstronomical } = values;

  return (
    <Modal isOpen={modal} toggle={toggle}>
      <ModalHeader toggle={toggle}>Создание календаря</ModalHeader>
      <ModalBody>
        <FormGroup>
          <Label for="name">Название календаря</Label>
          <Input
            className="mb-2"
            value={calendarName}
            onChange={handleChange}
            placeholder="ВА РБ"
            invalid={errorsShown && !!errors["calendarName"]}
          />
          <FormFeedback>{errors["calendarName"]}</FormFeedback>
        </FormGroup>
        <FormGroup>
          <Row>
            <Col className="" md="6">
              <Label for="name">Начало календаря</Label>
              <DatePicker
                selected={dateFrom}
                selectsStart
                startDate={dateFrom}
                endDate={dateTo}
                onChange={handleStartDate}
                locale="ru"
                dateFormat="dd/MM/yyyy"
                className={classnames("form-control", {
                  "is-invalid":
                    errors["range"] || (errors["dateFrom"] && errorsShown),
                })}
              />
            </Col>
            <Col className="" md="6">
              <Label for="name">Конец календаря</Label>
              <DatePicker
                selected={dateTo}
                selectsEnd
                endDate={dateTo}
                onChange={handleEndDate}
                locale="ru"
                dateFormat="dd/MM/yyyy"
                className={classnames("form-control", {
                  "is-invalid":
                    errors["range"] || (errors["dateTo"] && errorsShown),
                })}
              />
            </Col>
          </Row>
          <FormFeedback
            className={classnames({
              "d-block":
                errorsShown &&
                (errors["range"] || errors["dateFrom"] || errors["dateTo"]),
            })}
          >
            {errors["range"] || errors["dateFrom"] || errors["dateTo"]}
          </FormFeedback>
        </FormGroup>
        <FormGroup check>
          <Label check>
            <Input
              checked={isAstronomical}
              onChange={handleCheckboxChange}
              type="checkbox"
              id="isAstronomical"
            />{" "}
            Астрономическое время
          </Label>
        </FormGroup>
      </ModalBody>
      <ModalFooter>
        <Button color="primary" onClick={onSubmit}>
          Создать
        </Button>{" "}
        <Button color="secondary" onClick={toggle}>
          Отменить
        </Button>
      </ModalFooter>
    </Modal>
  );
};

CalendarListModal.propTypes = {
  modal: PropTypes.bool,
  onToggle: PropTypes.func,
  onSubmit: PropTypes.func,
};
