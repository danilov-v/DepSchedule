import React from "react";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { Router } from "react-router-dom";
import { registerLocale } from "react-datepicker";
import { library } from "@fortawesome/fontawesome-svg-core";
import { fab } from "@fortawesome/free-brands-svg-icons";
import { fas } from "@fortawesome/free-solid-svg-icons";
import ru from "date-fns/locale/ru";

import createStore from "store";
import { Main } from "pages/main/main";
import { history } from "helpers/history";

import "react-datepicker/dist/react-datepicker.css";
import "./app.scss";

registerLocale("ru", ru); //set russion locale for date time picker
library.add(fab, fas); // Add default font-aweosome icons

function App() {
  const { store, persistor } = createStore();
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <Router history={history}>
          <Main />
        </Router>
      </PersistGate>
    </Provider>
  );
}

export default App;
