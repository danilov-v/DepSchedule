import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";

import "./calendar.scss";

export function CalendarCell({ text, fluid }) {
  return (
    <div
      className={classnames(
        "calendar-cell d-flex align-items-center justify-content-center",
        {
          fluid,
        }
      )}
    >
      {text}
    </div>
  );
}

CalendarCell.propTypes = {
  text: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.number,
    PropTypes.node,
  ]),
  fluid: PropTypes.bool,
};

CalendarCell.defaultProps = {
  text: null,
  fluid: false,
};
