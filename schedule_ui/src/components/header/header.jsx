import React, { useState } from "react";
import { Link } from "react-router-dom";
import { withRouter } from "react-router";
import classnames from "classnames";
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
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import DatePicker from "react-datepicker";
import PropTypes from "prop-types";
import logo from "logo.png";
import { useAuth } from "components/auth-service/auth-service";

import "./header.scss";

export function HeaderUI({
  startDate,
  endDate,
  operationalDate,
  onChangeOperationalDate,
  onChangeStartDate,
  onChangeEndDate,
  history,
}) {
  const [isOpen, toggleNav] = useState(false);
  const { logout } = useAuth();

  const toggle = () => toggleNav(!isOpen);
  const print = () => window.print();

  const isHomePage = history.location.pathname === "/";

  return (
    <Navbar color="light" light expand="lg" className="header mb-3">
      <Link className="nav-link brand" to="/">
        <img src={logo} alt="Logo" className="logo" />
        ГРСУ
      </Link>
      <NavbarToggler onClick={toggle} />
      <Collapse isOpen={isOpen} navbar>
        <Nav className="ml-auto" navbar>
          <div
            className={classnames("d-flex align-items-center mr-5", {
              invisible: !isHomePage,
            })}
          >
            <span>Оперативное время: &nbsp;</span>
            <DatePicker
              selected={operationalDate}
              dateFormat="dd/MM/yyyy"
              onChange={onChangeOperationalDate}
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
              onChange={onChangeStartDate}
              locale="ru"
              className="date_picker"
            />
            &nbsp; по &nbsp;
            <DatePicker
              selected={endDate}
              minDate={startDate}
              dateFormat="dd/MM/yyyy"
              onChange={onChangeEndDate}
              locale="ru"
              className="date_picker"
            />
          </div>
          <NavItem>
            <Link className="nav-link" to="event_types">
              Типы событий
            </Link>
          </NavItem>
          <NavItem>
            <Link className="nav-link" to="periods">
              Периоды
            </Link>
          </NavItem>
          <NavItem>
            <NavLink tag={Link} to="finished_events">
              <FontAwesomeIcon icon="list-ul" />
            </NavLink>
          </NavItem>
          <NavItem hidden={!isHomePage}>
            <NavLink href="#" onClick={print}>
              <FontAwesomeIcon icon="file-pdf" />
            </NavLink>
          </NavItem>
          <UncontrolledDropdown nav inNavbar>
            <DropdownToggle nav>
              <FontAwesomeIcon icon="user-alt" />
            </DropdownToggle>
            <DropdownMenu right>
              <DropdownItem>Профиль</DropdownItem>
              <DropdownItem divider />
              <DropdownItem onClick={logout}>Выйти</DropdownItem>
            </DropdownMenu>
          </UncontrolledDropdown>
        </Nav>
      </Collapse>
    </Navbar>
  );
}

export const Header = withRouter(HeaderUI);

HeaderUI.propTypes = {
  startDate: PropTypes.instanceOf(Date),
  endDate: PropTypes.instanceOf(Date),
  operationalDate: PropTypes.instanceOf(Date),
  onChangeStartDate: PropTypes.func,
  onChangeEndDate: PropTypes.func,
  history: PropTypes.object,
};
