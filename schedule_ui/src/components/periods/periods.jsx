import React, { useEffect } from "react";
import PropTypes from "prop-types";
import { useSelector, useDispatch } from "react-redux";
import { getAuthData } from "redux/selectors/auth";
import { getPeriodFormSelector } from "redux/selectors/ui";
import { getPeriodsSelector } from "redux/selectors/periods";
import {
  createPeriod,
  updatePeriod,
  removePeriod,
  fetchPeriods,
} from "redux/actions/periods";
import { openPeriodForm, closePeriodForm } from "redux/actions/forms";
import { Button, Container } from "reactstrap";
import { Title } from "components/title/title";
import { PERIOD_CONFIRMATION_OPTIONS } from "constants/confirmations";
import { useConfirmation } from "components/confirmation-service/confirmation-service";
import { PeriodsList } from "./periods-list";
import { PeriodsPopup } from "./periods-popup";
import { MANAGE_PERIODS } from "constants/permissions";
import { checkPermission } from "utils/permissions";

export function Periods() {
  const periods = useSelector(getPeriodsSelector);
  const { isOpen, isEdit, initialFormData, error } = useSelector(
    getPeriodFormSelector
  );
  const { role } = useSelector(getAuthData);
  const dispatch = useDispatch();
  const confirm = useConfirmation();

  useEffect(() => {
    dispatch(fetchPeriods());
  }, [dispatch]);

  const onPeriodSubmit = periodData =>
    isEdit
      ? dispatch(updatePeriod(periodData))
      : dispatch(createPeriod(periodData));

  const tryToRemove = periodId => {
    confirm(PERIOD_CONFIRMATION_OPTIONS).then(() => {
      dispatch(removePeriod(periodId));
    });
  };

  const openForm = (isEdit, formData = null) => {
    if (!isOpen && isEdit)
      formData = {
        ...formData,
        startDate: new Date(formData.startDate),
        endDate: new Date(formData.endDate),
      };

    dispatch(openPeriodForm(isEdit, formData));
  };

  const closeForm = () => dispatch(closePeriodForm());

  return (
    <Container>
      <Title text="Периоды" />
      <PeriodsList
        onPeriodEdit={openForm.bind(null, true)}
        onPeriodRemove={tryToRemove}
        periods={periods}
        userRole={role}
      />
      <Button
        outline
        color="primary"
        size="lg"
        className="font-weight-bold float-right"
        onClick={openForm.bind(null, false, undefined)}
        hidden={!checkPermission(role, MANAGE_PERIODS)}
      >
        +
      </Button>
      <PeriodsPopup
        isEdit={isEdit}
        isOpen={isOpen}
        toggle={closeForm}
        onPeriodSubmit={onPeriodSubmit}
        defaultFormData={initialFormData}
        error={error}
      />
    </Container>
  );
}

Periods.propTypes = {
  periods: PropTypes.arrayOf(PropTypes.shape({})),
};

Periods.defaultProps = {
  periods: [],
};
