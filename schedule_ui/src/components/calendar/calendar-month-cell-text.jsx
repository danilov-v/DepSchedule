import React from "react";
import PropTypes from "prop-types";
import { CELL_WIDTH, CELL_WIDTH_BORDER } from "constants/calendar";

export function CalendarMonthCellText({ text, length }) {
  return (
    <p
      className="m-0 calendar-month-cell-text"
      style={{ maxWidth: length * (CELL_WIDTH - CELL_WIDTH_BORDER) }}
    >
      {text}
    </p>
  );
}

CalendarMonthCellText.propTypes = {
  text: PropTypes.string,
  length: PropTypes.number,
};

CalendarMonthCellText.defaultProps = {
  text: "",
  length: 0,
};
