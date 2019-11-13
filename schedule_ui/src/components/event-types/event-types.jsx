import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
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
import { openForm, closeForm } from "redux/actions/forms";
import { EVENT_TYPE_CONFIRMATION_OPTIONS } from "constants/confirmations";
import { EVENT_TYPE_FORM } from "constants/forms";
import { MANAGE_EVENT_TYPES } from "constants/permissions";
import { checkPermission } from "utils/permissions";

import "./event-types.scss";

export function EventTypes() {
  const eventTypes = useSelector(getEventTypesSelector);
  const dispatch = useDispatch();
  const confirm = useConfirmation();
  const { role } = useSelector(getAuthData);

  useEffect(() => {
    dispatch(fetchEventTypes());
  }, [dispatch]);

  const openNewForm = () => dispatch(openForm({ formName: EVENT_TYPE_FORM }));
  const openEditForm = formData =>
    dispatch(openForm({ formName: EVENT_TYPE_FORM, isEdit: true, formData }));

  const onCloseForm = () => dispatch(closeForm());

  const onEventTypeCreate = values => dispatch(createEventType(values));

  const onEventTypeUpdate = values => dispatch(editEventType(values));

  const onEventTypeSubmit = (values, isEdit = false) =>
    isEdit ? onEventTypeUpdate(values) : onEventTypeCreate(values);

  const onEventTypeRemove = typeId =>
    confirm(EVENT_TYPE_CONFIRMATION_OPTIONS).then(() =>
      dispatch(removeEventType(typeId))
    );

  return (
    <Container>
      <Title text="Типы событий" />
      <EventTypesList
        eventTypes={eventTypes}
        onEventTypeEdit={openEditForm}
        onEventTypeRemove={onEventTypeRemove}
        role={role}
      />
      <Button
        outline
        color="primary"
        size="lg"
        className="font-weight-bold float-right"
        onClick={openNewForm}
        hidden={!checkPermission(role, MANAGE_EVENT_TYPES)}
      >
        +
      </Button>
      <EventTypePopup
        toggle={onCloseForm}
        eventTypes={eventTypes}
        onEventTypeSubmit={onEventTypeSubmit}
      />
    </Container>
  );
}
