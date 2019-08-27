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

import "./header.scss";

function Header({
  startDate,
  endDate,
  onChangeStartDate,
  onChangeEndDate,
  history,
}) {
  const [isOpen, toggleNav] = useState(false);
  const toggle = () => toggleNav(!isOpen);
  const print = () => window.print();

  const isHomePage = history.location.pathname === "/";

  return (
    <Navbar color="light" light expand="lg" className="header mb-3">
      <Link className="nav-link brand" to="/">
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
            События с &nbsp;
            <DatePicker
              selected={startDate}
              dateFormat="dd/MM/yyyy"
              onChange={date => onChangeStartDate(date)}
              locale="ru"
              className="date_picker"
            />
            &nbsp; по &nbsp;
            <DatePicker
              selected={endDate}
              minDate={startDate}
              dateFormat="dd/MM/yyyy"
              onChange={date => onChangeEndDate(date)}
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
            <NavLink href="#">Секции</NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              className={classnames({ "d-none": !isHomePage })}
              href="#"
              onClick={print}
            >
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
              <DropdownItem>Выйти</DropdownItem>
            </DropdownMenu>
          </UncontrolledDropdown>
        </Nav>
      </Collapse>
    </Navbar>
  );
}

Header.propTypes = {
  startDate: PropTypes.instanceOf(Date),
  endDate: PropTypes.instanceOf(Date),
  onChangeStartDate: PropTypes.func,
  onChangeEndDate: PropTypes.func,
  history: PropTypes.object,
};

export const NavBar = withRouter(Header);
