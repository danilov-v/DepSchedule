import React from "react";
import { BrowserRouter } from "react-router-dom";

import { registerLocale } from "react-datepicker";
import ru from "date-fns/locale/ru";
import { library } from "@fortawesome/fontawesome-svg-core";
import { fab } from "@fortawesome/free-brands-svg-icons";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { Auth } from "pages/auth/auth";

import "react-datepicker/dist/react-datepicker.css";
import "./app.scss";

registerLocale("ru", ru); //set russion locale for date time picker
library.add(fab, fas); // Add default font-aweosome icons

function App() {
  return (
    <BrowserRouter>
      <Auth />
    </BrowserRouter>
  );
}

export default App;
