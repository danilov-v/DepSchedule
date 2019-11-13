import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";

export function EventCell({ marked, unitId, date }) {
  return (
    <div
      data-unit-id={unitId}
      data-date={date}
      className={classnames("event-cell", { "event-cell-marked": marked })}
    />
  );
}

EventCell.propTypes = {
  unitId: PropTypes.number,
  date: PropTypes.instanceOf(Date),
  marked: PropTypes.bool,
};

EventCell.defaultProps = {
  hasEvent: false,
  marked: false,
};
