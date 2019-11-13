import { createSelector } from "reselect";
import {
  UNIT_FORM,
  EVENT_FORM,
  PERIOD_FORM,
  EVENT_TYPE_FORM,
} from "constants/forms";

export const getLoginErrorSelector = state => state.forms.loginForm;

export const getActiveForm = state => state.forms.activeForm;

export const getUnitFormSelector = createSelector(
  getActiveForm,
  activeForm => (activeForm.formName === UNIT_FORM ? activeForm : {})
);

export const getEventFormSelector = createSelector(
  getActiveForm,
  activeForm => (activeForm.formName === EVENT_FORM ? activeForm : {})
);

export const getPeriodFormSelector = createSelector(
  getActiveForm,
  activeForm => (activeForm.formName === PERIOD_FORM ? activeForm : {})
);

export const getEventTypeFormSelector = createSelector(
  getActiveForm,
  activeForm => (activeForm.formName === EVENT_TYPE_FORM ? activeForm : {})
);
