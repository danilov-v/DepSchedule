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
import { OutsideListener } from "components/outside-listener/outside-listener";
import { getEventsSelector } from "redux/selectors/event";
import { getUserDataSelector } from "redux/selectors/ui";
import { fetchLastEvents } from "redux/actions/event";

import "./last-events-list.scss";

export const LastEventsList = () => {
  const [popoverOpen, setPopoverOpen] = useState(false);
  const toggle = () => setPopoverOpen(!popoverOpen);

  const dispatch = useDispatch();
  const lastEvents = useSelector(getEventsSelector);
  const { startDate } = useSelector(getUserDataSelector);

  useEffect(() => {
    if (popoverOpen) {
      dispatch(fetchLastEvents());
    }
  }, [dispatch, popoverOpen]);

  const isEventOnGraphic = event =>
    isBefore(new Date(event.dateFrom), new Date(startDate));

  return (
    <>
      <div className="nav-link last-events-list-icon" id="last-events-btn">
        <FontAwesomeIcon icon="list-ul" />
      </div>

      <Popover
        placement="bottom-end"
        trigger="click"
        isOpen={popoverOpen}
        target="last-events-btn"
        toggle={toggle}
        fade={false}
      >
        <OutsideListener callback={toggle}>
          <>
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
                        el.scrollIntoView({
                          behavior: "instant",
                          block: "end",
                        });
                      }}
                      to={`/#event-${event.eventId}`}
                      className="text-dark"
                    >
                      <div className="text-muted">{event.unitTitle}</div>
                      <Badge
                        color="info"
                        className="event-title"
                        style={{ background: event.color }}
                      >
                        {event.eventTypeDescription}
                      </Badge>
                      <span className="sub-text">
                        закончилось {event.dateTo}
                      </span>
                    </HashLink>
                  </ListGroupItem>
                ))}
              </ListGroup>
            </PopoverBody>
          </>
        </OutsideListener>
      </Popover>
    </>
  );
};
