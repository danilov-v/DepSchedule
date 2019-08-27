import React, { useState } from "react";
import { Link } from "react-router-dom";
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

import "./header.scss";

export function Header({
  startDate,
  endDate,
  onChangeStartDate,
  onChangeEndDate,
}) {
  const [isOpen, toggleNav] = useState(false);
  const toggle = () => toggleNav(!isOpen);
  const print = () => window.print();

  return (
    <Navbar color="light" light expand="lg" className="header mb-3">
      <Link className="nav-link brand" to="/">
        ГРСУ
      </Link>
      <NavbarToggler onClick={toggle} />
      <Collapse isOpen={isOpen} navbar>
        <Nav className="ml-auto" navbar>
          <div className="d-flex align-items-center mr-5">
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
            <NavLink href="#" onClick={print}>
              <FontAwesomeIcon className="ml-1" icon="file-pdf" />
            </NavLink>
          </NavItem>
          <UncontrolledDropdown nav inNavbar>
            <DropdownToggle nav caret>
              <FontAwesomeIcon className="ml-1" icon="user-alt" />
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
