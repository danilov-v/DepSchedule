import { combineReducers } from "redux";
import calendars from "./calendars";
import auth from "./auth";
import init from "./init";
import ui from "./ui";
import scheduler from "./scheduler";
import eventTypes from "./event-types";

export default combineReducers({
  init,
  auth,
  calendars,
  ui,
  scheduler,
  eventTypes,
});
