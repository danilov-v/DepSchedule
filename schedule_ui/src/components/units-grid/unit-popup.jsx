import React, { useState, useRef } from "react";
import PropTypes from "prop-types";
import { useDispatch } from "react-redux";
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
} from "reactstrap";
import { get, isBoolean, set } from "lodash";
import { FAILED_UNIT_NOTIFICATION_DATA } from "constants/notifications";
import { FLAGS_MAP, NO_FLAG } from "constants/flags";
import { NotificationManager } from "helpers/notification-manager";
import { createUnit, updateUnit } from "redux/actions/units";

import "./unit-popup.scss";

const DEFAULT_FORM_DATA = {
  title: "",
  parentId: 0,
  flag: NO_FLAG.url,
  planned: false,
  location: "",
};

const validate = formData =>
  Boolean(formData.title && formData.title.length >= 2);

const setCorrectFlagName = formData =>
  set(
    formData,
    "flag",
    get(
      FLAGS_MAP.find(
        item => item.url === formData.flag || item.urlDashed === formData.flag
      ),
      formData.planned ? "urlDashed" : "url",
      null
    )
  );

export function UnitPopup({ units, unit, isEdit, onClose }) {
  const dispatch = useDispatch();
  const [isValid, setValidState] = useState(null);
  const [formData, setData] = useState(unit);
  const titleInputEl = useRef(null);
  const toggleModal = () => {
    onClose();
  };
  const onInputChange = event => {
    const newFormData = { ...formData, title: get(event, "target.value") };

    setValidState(validate(newFormData));
    setData(newFormData);
  };
  const onLocationChange = event =>
    setData({ ...formData, location: get(event, "target.value") });
  const onSelectChange = event =>
    setData({ ...formData, parentId: +get(event, "target.value") });

  const onSubmit = async event => {
    event.preventDefault();

    if (validate(formData)) {
      setCorrectFlagName(formData);

      if (isEdit) {
        dispatch(updateUnit({ unit: formData }));
      } else {
        dispatch(createUnit({ unit: formData }));
      }

      toggleModal();
    } else {
      setValidState(false);
      NotificationManager.fire(FAILED_UNIT_NOTIFICATION_DATA);
    }

    return false;
  };

  const onEnter = () => {
    setTimeout(() => {
      if (titleInputEl.current) titleInputEl.current.focus();
    });
  };

  const onFlagChange = flag => {
    const { url } = flag;
    const newFormData = { ...formData, flag: url };

    setData(newFormData);
  };

  const onPlannedChange = event => {
    const { checked } = event.target;

    setData({ ...formData, planned: checked });
  };

  return (
    <Modal
      className="unit-popup"
      isOpen={true}
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
              valid={isValid}
              invalid={isBoolean(isValid) && !isValid}
              onChange={onInputChange}
              value={formData.title}
              innerRef={titleInputEl}
            />
          </FormGroup>
          <FormGroup>
            <Label for="unitParent">Родительское Подразделение</Label>
            <Input
              type="select"
              name="unitParent"
              id="unitParent"
              onChange={onSelectChange}
              defaultValue={formData.parentId}
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
              onChange={onLocationChange}
              value={formData.location}
            />
          </FormGroup>
          <FormGroup>
            <UncontrolledDropdown>
              <DropdownToggle tag="label">
                Флаг Подразделения
                <div>
                  {formData.flag ? (
                    <img
                      src={formData.flag}
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
                checked={formData.planned}
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
  unit: PropTypes.shape({
    unitId: PropTypes.number,
    parentId: PropTypes.number,
    title: PropTypes.string,
    location: PropTypes.string,
  }),
  onClose: PropTypes.func,
};

UnitPopup.defaultProps = {
  unit: DEFAULT_FORM_DATA,
  onClose: () => {},
};
