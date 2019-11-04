import { combineReducers } from "redux";
import calendars from "./calendars";
import auth from "./auth";
import init from "./init";
import ui from "./ui";
import forms from "./forms";
import units from "./units";
import periods from "./periods";
import event from "./event";
import eventTypes from "./event-types";

export default combineReducers({
  init,
  ui,
  auth,
  calendars,
  forms,
  units,
  periods,
  event,
  eventTypes,
});
