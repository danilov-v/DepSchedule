import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";

export function EventCell({ onClick, hasEvent }) {
  return (
    <div
      className={classnames("event-cell", { "event-cell-disabled": hasEvent })}
      onClick={onClick}
    />
  );
}

EventCell.propTypes = {
  onClick: PropTypes.func,
};
