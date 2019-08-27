import React, { useState } from "react";
import PropTypes from "prop-types";
import { Button, Container } from "reactstrap";
import { createEventType, updateEventType, removeEventType } from "helpers/api";
import { Title } from "components/title/title";
import { EventTypesList } from "./event-types-list";
import { EventTypePopup } from "./event-type-popup";

import "./event-types.scss";

const DEFAULT_EVENT_TYPE = {
  color: "#f44336",
  description: "",
  typeId: null,
};

export function EventTypes({ eventTypes, onEventTypesUpdate }) {
  const [isFormOpen, toggleForm] = useState(false);
  const [formType, setFormType] = useState("create");
  const [defaultFormData, setDefaultFormData] = useState(DEFAULT_EVENT_TYPE);

  const toggleEventTypeForm = (type, defaultFormData = DEFAULT_EVENT_TYPE) => {
    setFormType(type);
    setDefaultFormData({ ...defaultFormData });
    toggleForm(!isFormOpen);
  };

  const onEventTypeCreate = values =>
    createEventType(values).then(onEventTypesUpdate);

  const onEventTypeUpdate = values =>
    updateEventType(values).then(onEventTypesUpdate);

  const onEventTypeRemove = typeId =>
    removeEventType(typeId).then(onEventTypesUpdate);

  return (
    <Container>
      <Title text="Типы событий" />
      <EventTypesList
        eventTypes={eventTypes}
        onEventTypeClick={toggleEventTypeForm.bind(null, "edit")}
        onEventTypeRemove={onEventTypeRemove}
      />
      <Button
        outline
        color="primary"
        size="lg"
        className="font-weight-bold float-right"
        onClick={() => toggleEventTypeForm("create")}
      >
        +
      </Button>
      <EventTypePopup
        type={formType}
        isOpen={isFormOpen}
        toggle={() => toggleEventTypeForm(null)}
        onEventTypeSubmit={
          formType === "create" ? onEventTypeCreate : onEventTypeUpdate
        }
        defaultFormData={defaultFormData}
        eventTypes={eventTypes}
        onEventRemove={onEventTypeRemove}
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
  onEventTypesUpdate: PropTypes.func,
};
