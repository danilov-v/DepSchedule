import React, { useState, useEffect } from "react";
import { HashLink } from "react-router-hash-link";
import { useSelector, useDispatch } from "react-redux";
import { isBefore } from "date-fns";
import {
  ListGroup,
  ListGroupItem,
  Popover,
  PopoverHeader,
  PopoverBody,
  Badge,
} from "reactstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { getEventsSelector } from "redux/selectors/event";
import { getActiveCalendarInfo } from "redux/selectors/calendars";
import { fetchLastEvents } from "redux/actions/event";

import "./last-events-list.scss";

export const LastEventsList = () => {
  const [popoverOpen, setPopoverOpen] = useState(false);
  const toggle = () => setPopoverOpen(!popoverOpen);

  const dispatch = useDispatch();
  const lastEvents = useSelector(getEventsSelector);
  const { startDate } = useSelector(getActiveCalendarInfo);

  useEffect(() => {
    dispatch(fetchLastEvents());
  }, [dispatch]);

  const isEventOnGraphic = event =>
    isBefore(new Date(event.dateFrom), new Date(startDate));

  return (
    <>
      <div className="nav-link last-events-list-icon ">
        <FontAwesomeIcon icon="list-ul" id="Popover1" />
      </div>

      <Popover
        placement="bottom-end"
        isOpen={popoverOpen}
        target="Popover1"
        toggle={toggle}
        fade={false}
        hideArrow
      >
        <PopoverHeader>Последние события</PopoverHeader>
        <PopoverBody className="last-events-list">
          <ListGroup>
            {lastEvents.map(event => (
              <ListGroupItem
                key={`recent-${event.eventId}`}
                disabled={isEventOnGraphic(event)}
              >
                <HashLink
                  scroll={el => {
                    el.focus();
                    el.scrollIntoView({ behavior: "instant", block: "end" });
                  }}
                  to={`/#event-${event.eventId}`}
                  className="text-dark"
                >
                  <Badge color="info" className="event-title">
                    {event.unitTitle}
                  </Badge>
                  <div className="text-muted">{event.eventTypeDescription}</div>
                  <span className="sub-text">закончилось {event.dateTo}</span>
                </HashLink>
              </ListGroupItem>
            ))}
          </ListGroup>
        </PopoverBody>
      </Popover>
    </>
  );
};
