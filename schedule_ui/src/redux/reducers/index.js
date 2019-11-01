import { combineReducers } from "redux";
import calendars from "./calendars";
import auth from "./auth";
import init from "./init";
import user from "./user";
import ui from "./ui";
import units from "./units";
import periods from "./periods";
import event from "./event";
import eventTypes from "./event-types";

export default combineReducers({
  init,
  user,
  auth,
  calendars,
  ui,
  units,
  periods,
  event,
  eventTypes,
});
