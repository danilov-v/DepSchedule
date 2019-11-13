import React, { useRef } from "react";
import PropTypes from "prop-types";
import { useDispatch, useSelector } from "react-redux";
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
  DropdownMenu,
  DropdownItem,
  UncontrolledDropdown,
  DropdownToggle,
  FormFeedback,
} from "reactstrap";
import { get, set } from "lodash";
import { FLAGS_MAP, NO_FLAG } from "constants/flags";
import { useForm } from "helpers/hooks/useForm";
import { createUnit, updateUnit } from "redux/actions/units";
import { closeForm } from "redux/actions/forms";
import { getUnitFormSelector } from "redux/selectors/forms";

import "./unit-popup.scss";

const DEFAULT_FORM_DATA = {
  title: "",
  parentId: 0,
  flag: NO_FLAG.url,
  planned: false,
  location: "",
};

const validateEventForm = values => {
  let errors = {};

  if (!values.title) errors["unitTitle"] = "Введите название подразделения";

  if (!values.location) errors["unitLocation"] = "Введите название локации";

  return errors;
};

const getFininalFormData = formData => {
  const data = { ...formData };
  set(
    data,
    "flag",
    get(
      FLAGS_MAP.find(
        item => item.url === formData.flag || item.urlDashed === formData.flag
      ),
      formData.planned ? "urlDashed" : "url",
      null
    )
  );

  return data;
};

export function UnitPopup({ units }) {
  const dispatch = useDispatch();
  const titleInputEl = useRef(null);
  const { formData = DEFAULT_FORM_DATA, isEdit, isOpen } = useSelector(
    getUnitFormSelector
  );
  const { onChange, onSubmit, errors, errorsShown, values } = useForm(
    submitForm,
    formData,
    validateEventForm
  );
  const toggleModal = () => dispatch(closeForm());
  const onInputChange = event =>
    onChange({ title: get(event, "target.value") });
  const onLocationChange = event =>
    onChange({ location: get(event, "target.value") });
  const onSelectChange = event =>
    onChange({ parentId: +get(event, "target.value") });

  const onFlagChange = flag => onChange({ flag: flag.url });

  const onPlannedChange = event => onChange({ planned: event.target.checked });

  const onEnter = () => {
    setTimeout(() => {
      if (titleInputEl.current) titleInputEl.current.focus();
    });
  };

  function submitForm() {
    const unit = getFininalFormData(values);

    if (isEdit) {
      dispatch(updateUnit({ unit }));
    } else {
      dispatch(createUnit({ unit }));
    }
  }

  return (
    <Modal
      className="unit-popup"
      isOpen={isOpen}
      toggle={toggleModal}
      onEnter={onEnter}
    >
      <Form className="p-3" onSubmit={onSubmit}>
        <ModalHeader toggle={toggleModal}>
          {isEdit ? "Редактирование" : "Создание"} Подразделения
        </ModalHeader>
        <ModalBody>
          <FormGroup>
            <Label for="unitTitle">Название Подразделения</Label>
            <Input
              name="unitTitle"
              id="unitTitle"
              placeholder="СУ"
              invalid={errorsShown && !!errors["unitTitle"]}
              onChange={onInputChange}
              value={values.title}
              innerRef={titleInputEl}
            />
            <FormFeedback>{errors["unitTitle"]}</FormFeedback>
          </FormGroup>
          <FormGroup>
            <Label for="unitParent">Родительское Подразделение</Label>
            <Input
              type="select"
              name="unitParent"
              id="unitParent"
              onChange={onSelectChange}
              defaultValue={values.parentId}
            >
              <option value={0}>Новое Подразделение</option>
              {units.map((unit, i) => (
                <option key={unit.title + i} value={unit.unitId}>
                  {unit.title}
                </option>
              ))}
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="unitLocation">Локация</Label>
            <Input
              name="unitLocation"
              id="unitLocation"
              placeholder="Минск"
              invalid={errorsShown && !!errors["unitLocation"]}
              onChange={onLocationChange}
              value={values.location}
            />
            <FormFeedback>{errors["unitLocation"]}</FormFeedback>
          </FormGroup>
          <FormGroup>
            <UncontrolledDropdown>
              <DropdownToggle tag="label">
                Флаг Подразделения
                <div>
                  {values.flag ? (
                    <img
                      src={values.flag}
                      className="unit-popup-flag-item"
                      alt="flag preview"
                    />
                  ) : (
                    <span className="font-italic">{NO_FLAG.text}</span>
                  )}
                </div>
              </DropdownToggle>

              <DropdownMenu>
                <DropdownItem
                  value={NO_FLAG.id}
                  onClick={() => onFlagChange(NO_FLAG)}
                >
                  <span>{NO_FLAG.text}</span>
                </DropdownItem>
                {FLAGS_MAP.map(flag => (
                  <DropdownItem
                    key={flag.id}
                    value={flag.id}
                    onClick={() => onFlagChange(flag)}
                  >
                    <img
                      src={flag.url}
                      className="unit-popup-flag-item"
                      alt="flag drop-down-preview"
                    />
                  </DropdownItem>
                ))}
              </DropdownMenu>
            </UncontrolledDropdown>
          </FormGroup>
          <FormGroup check>
            <Label for="unitPlanned" check>
              <Input
                type="checkbox"
                name="unitPlanned"
                id="unitPlanned"
                onChange={onPlannedChange}
                checked={values.planned}
              />
              Запланировано
            </Label>
          </FormGroup>
        </ModalBody>
        <ModalFooter>
          <Button type="submit" color="success" className="mr-3">
            {isEdit ? "Обновить" : "Создать"}
          </Button>
          <Button color="primary" onClick={toggleModal}>
            Закрыть
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
}

UnitPopup.propTypes = {
  units: PropTypes.arrayOf(PropTypes.shape({})),
};

UnitPopup.defaultProps = {
  units: [],
};
