import React from "react";

import { registerLocale } from "react-datepicker";
import ru from "date-fns/locale/ru";
import { Auth } from "pages/auth/auth";

import "react-datepicker/dist/react-datepicker.css";
import "./app.scss";

registerLocale("ru", ru); //set russion locale for date time picker

function App() {
  return <Auth />;
}

export default App;
