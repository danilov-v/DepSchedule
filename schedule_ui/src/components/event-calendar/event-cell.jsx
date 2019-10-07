import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";

export function EventCell({ onClick, hasEvent, marked }) {
  return (
    <div
      className={classnames(
        "event-cell",
        { "event-cell-disabled": hasEvent },
        { "event-cell-marked": marked }
      )}
      onClick={onClick}
    />
  );
}

EventCell.propTypes = {
  onClick: PropTypes.func,
  hasEvent: PropTypes.bool,
  marked: PropTypes.bool,
};

EventCell.defaultProps = {
  hasEvent: false,
  marked: false,
};
