import React, { useState } from "react";
import PropTypes from "prop-types";
import { Button, Container } from "reactstrap";
import { Title } from "components/title/title";
import { removePeriod, createPeriod, updatePeriod } from "helpers/api";
import { PERIOD_CONFIRMATION_OPTIONS } from "constants/confirmations";
import { useConfirmation } from "components/confirmation-service/confirmation-service";
import { PeriodsList } from "./periods-list";
import { PeriodsPopup } from "./periods-popup";

const DEFAULT_PERIOD = {
  name: "",
  startDate: null,
  endDate: null,
};

export function Periods({ periods, onPeriodsUpdate }) {
  const [isFormOpen, toggleForm] = useState(false);
  const [formType, setFormType] = useState("create");
  const [defaultFormData, setDefaultFormData] = useState(DEFAULT_PERIOD);

  const confirm = useConfirmation();

  const onPeriodSubmit = periodData =>
    formType === "create"
      ? createPeriod(periodData).then(onPeriodsUpdate)
      : updatePeriod(periodData).then(onPeriodsUpdate);

  const tryToRemove = periodId => {
    confirm(PERIOD_CONFIRMATION_OPTIONS)
      .then(removePeriod.bind(null, periodId))
      .then(onPeriodsUpdate);
  };

  const toggle = (type, formData = { ...DEFAULT_PERIOD }) => {
    if (type === "edit")
      formData = {
        ...formData,
        startDate: new Date(formData.startDate),
        endDate: new Date(formData.endDate),
      };

    setFormType(type);
    setDefaultFormData(formData);
    toggleForm(!isFormOpen);
  };

  return (
    <Container>
      <Title text="Периоды" />
      <PeriodsList
        onPeriodEdit={toggle.bind(null, "edit")}
        onPeriodRemove={tryToRemove}
        periods={periods}
      />
      <Button
        outline
        color="primary"
        size="lg"
        className="font-weight-bold float-right"
        onClick={toggle.bind(null, "create", undefined)}
      >
        +
      </Button>
      <PeriodsPopup
        type={formType}
        isOpen={isFormOpen}
        toggle={() => toggle(null)}
        onPeriodSubmit={onPeriodSubmit}
        defaultFormData={defaultFormData}
      />
    </Container>
  );
}

Periods.propTypes = {
  periods: PropTypes.arrayOf(PropTypes.shape({})),
  onPeriodsUpdate: PropTypes.func.isRequired,
};

Periods.defaultProps = {
  periods: [],
};
