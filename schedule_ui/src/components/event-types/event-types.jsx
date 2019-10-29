import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import PropTypes from "prop-types";
import { Button, Container } from "reactstrap";
import { getEventTypesSelector } from "redux/selectors/event-types";
import { getAuthData } from "redux/selectors/auth";
import { useConfirmation } from "components/confirmation-service/confirmation-service";
import { Title } from "components/title/title";
import { EventTypesList } from "./event-types-list";
import { EventTypePopup } from "./event-type-popup";
import {
  fetchEventTypes,
  createEventType,
  editEventType,
  removeEventType,
} from "redux/actions/event-types";
import { openEventTypeForm, closeEventTypeForm } from "redux/actions/forms";
import { getEventTypeFormSelector } from "redux/selectors/ui";
import { EVENT_TYPE_CONFIRMATION_OPTIONS } from "constants/confirmations";
import { MANAGE_EVENT_TYPES } from "constants/permissions";
import { checkPermission } from "utils/permissions";

import "./event-types.scss";

export function EventTypes() {
  const eventTypes = useSelector(getEventTypesSelector);
  const dispatch = useDispatch();
  const confirm = useConfirmation();
  const { role } = useSelector(getAuthData);
  const { isOpen, isEdit, initialFormData, error } = useSelector(
    getEventTypeFormSelector
  );

  useEffect(() => {
    dispatch(fetchEventTypes());
  }, [dispatch]);

  const openForm = (isEdit, initialFormData = null) =>
    dispatch(openEventTypeForm({ isEdit, initialFormData }));

  const closeForm = () => dispatch(closeEventTypeForm());

  const onEventTypeCreate = values => dispatch(createEventType(values));

  const onEventTypeUpdate = values => dispatch(editEventType(values));

  const onEventTypeRemove = typeId =>
    confirm(EVENT_TYPE_CONFIRMATION_OPTIONS).then(() =>
      dispatch(removeEventType(typeId))
    );

  return (
    <Container>
      <Title text="Типы событий" />
      <EventTypesList
        eventTypes={eventTypes}
        onEventTypeClick={openForm.bind(null, true)}
        onEventTypeRemove={onEventTypeRemove}
        role={role}
      />
      <Button
        outline
        color="primary"
        size="lg"
        className="font-weight-bold float-right"
        onClick={openForm.bind(null, false, null)}
        hidden={!checkPermission(role, MANAGE_EVENT_TYPES)}
      >
        +
      </Button>
      <EventTypePopup
        isEdit={isEdit}
        isOpen={isOpen}
        toggle={closeForm}
        onEventTypeSubmit={isEdit ? onEventTypeUpdate : onEventTypeCreate}
        defaultFormData={initialFormData}
        eventTypes={eventTypes}
        onEventRemove={onEventTypeRemove}
        error={error}
      />
    </Container>
  );
}

EventTypes.propTypes = {
  eventTypes: PropTypes.arrayOf(
    PropTypes.shape({
      color: PropTypes.string,
      description: PropTypes.string,
      typeId: PropTypes.number,
    })
  ),
};
