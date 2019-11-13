import { createSelector } from "reselect";
import { UNIT_FORM } from "constants/forms";

export const getLoginErrorSelector = state => state.forms.loginForm;

export const getEventFormSelector = state => state.forms.eventForm;

export const getEventTypeFormSelector = state => state.forms.eventTypeForm;

export const getPeriodFormSelector = state => state.forms.periodForm;

export const getActiveForm = state => state.forms.activeForm;

export const getUnitFormSelector = createSelector(
  getActiveForm,
  activeForm => (activeForm.formName === UNIT_FORM ? activeForm : {})
);
