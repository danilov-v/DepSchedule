import React, { useState } from "react";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  Nav,
  NavItem,
  NavLink,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
} from "reactstrap";
import { addDays } from "date-fns";
import { useSelector, useDispatch } from "react-redux";
import { Link, useLocation } from "react-router-dom";
import classnames from "classnames";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import DatePicker from "react-datepicker";
import {
  updateStartDate,
  updateEndDate,
  setOperationalDate,
} from "redux/actions/scheduler";
import { logoutRequest } from "redux/actions/auth";
import {
  getStartDateSelector,
  getEndDateSelector,
  getOperationalDateSelector,
} from "redux/selectors/scheduler";
import { getActiveCalendar } from "redux/selectors/calendars";
import { setActiveCalendar } from "redux/actions/calendars";
import logo from "logo.png";
import { LastEventsList } from "components/last-events-list/last-events-list";

import "./header.scss";

export function Header() {
  const dispatch = useDispatch();
  const location = useLocation();
  const activeCalendar = useSelector(getActiveCalendar);

  const [isOpen, toggleNav] = useState(false);
  const startDate = useSelector(getStartDateSelector);
  const endDate = useSelector(getEndDateSelector);
  const operationalDate = useSelector(getOperationalDateSelector);
  const isHomePage = location.pathname === "/";

  // Handlers
  const changeStartDate = date => {
    dispatch(updateStartDate(date));
    changeOperationalDate(addDays(date, activeCalendar.shift));
  };
  const changeEndDate = date => dispatch(updateEndDate(date));
  const changeOperationalDate = date => dispatch(setOperationalDate(date));
  const changeCalendar = () =>
    dispatch(setActiveCalendar({ calendarId: null }));
  const logout = () => dispatch(logoutRequest());

  const toggle = () => toggleNav(!isOpen);
  const print = () => window.print();

  return (
    <Navbar
      color="light"
      light
      expand="lg"
      className="header mb-3 bg-transparent"
    >
      <Link className="nav-link brand" to="/">
        <img src={logo} alt="Logo" className="logo" />
        ГРСУ
      </Link>
      <NavbarToggler onClick={toggle} />
      <Collapse isOpen={isOpen} navbar>
        <Nav className="ml-auto" navbar>
          <div
            title="Оперативное время"
            className={classnames("d-flex align-items-center mr-5", {
              invisible: !isHomePage,
            })}
          >
            <span>Оперативное время: &nbsp;</span>
            <DatePicker
              selected={operationalDate}
              dateFormat="dd/MM/yyyy"
              onChange={changeOperationalDate}
              locale="ru"
              className="date_picker"
            />
          </div>
          <div
            className={classnames("d-flex align-items-center mr-5", {
              invisible: !isHomePage,
            })}
          >
            События с &nbsp;
            <DatePicker
              selected={startDate}
              dateFormat="dd/MM/yyyy"
              onChange={changeStartDate}
              locale="ru"
              className="date_picker"
            />
            &nbsp; по &nbsp;
            <DatePicker
              selected={endDate}
              minDate={startDate}
              dateFormat="dd/MM/yyyy"
              onChange={changeEndDate}
              locale="ru"
              className="date_picker"
            />
          </div>
          <NavItem title="Типы событий">
            <Link className="nav-link" to="event_types">
              Типы событий
            </Link>
          </NavItem>
          <NavItem title="Периоды">
            <Link className="nav-link" to="periods">
              Периоды
            </Link>
          </NavItem>
          <NavItem title="Последние события">
            <LastEventsList />
          </NavItem>
          <NavItem hidden={!isHomePage} title="Печать">
            <NavLink href="#" onClick={print}>
              <FontAwesomeIcon icon="file-pdf" />
            </NavLink>
          </NavItem>
          <UncontrolledDropdown nav inNavbar title="Управление">
            <DropdownToggle nav>
              <FontAwesomeIcon icon="user-alt" />
            </DropdownToggle>
            <DropdownMenu right>
              <DropdownItem onClick={changeCalendar}>
                Сменить календарь
              </DropdownItem>
              <DropdownItem divider />
              <DropdownItem onClick={logout}>Выйти</DropdownItem>
            </DropdownMenu>
          </UncontrolledDropdown>
        </Nav>
      </Collapse>
    </Navbar>
  );
}
