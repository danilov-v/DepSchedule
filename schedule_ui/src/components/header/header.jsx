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
import { useSelector, useDispatch } from "react-redux";
import { Link, useLocation } from "react-router-dom";
import classnames from "classnames";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import DatePicker from "react-datepicker";
import { updateCalendar } from "redux/actions/calendars";
import { logoutRequest } from "redux/actions/auth";
import { getActiveCalendarInfo } from "redux/selectors/calendars";
import { getUserDataSelector } from "redux/selectors/ui";
import { setActiveCalendar } from "redux/actions/calendars";
import { setStartDate, setEndDate } from "redux/actions/ui";
import logo from "logo.png";
import { LastEventsList } from "components/last-events-list/last-events-list";

import "./header.scss";

export function Header() {
  const dispatch = useDispatch();
  const location = useLocation();
  const { startDate, endDate, operationalDate } = useSelector(
    getActiveCalendarInfo
  );
  const { startDate: userStartDate, endDate: userEndDate } = useSelector(
    getUserDataSelector
  );

  const [isOpen, toggleNav] = useState(false);
  const isHomePage = location.pathname === "/";

  // Handlers
  const changeStartDate = date => dispatch(setStartDate(date));
  const changeEndDate = date => dispatch(setEndDate(date));
  const changeOperationalDate = date =>
    dispatch(updateCalendar({ operationalDate: date }));
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
              minDate={startDate}
              maxDate={endDate}
              selected={userStartDate}
              dateFormat="dd/MM/yyyy"
              onChange={changeStartDate}
              locale="ru"
              className="date_picker"
            />
            &nbsp; по &nbsp;
            <DatePicker
              selected={userEndDate}
              minDate={startDate}
              maxDate={endDate}
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
