import React from "react";
import PropTypes from "prop-types";
import DatePicker from "react-datepicker";

import "./title.scss";

export function Title({
  startDate,
  endDate,
  onChangeStartDate,
  onChangeEndDate,
}) {
  return (
    <div className="title">
      График развёртывания СУ в период с &nbsp;
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
  );
}

Title.propTypes = {
  onChangeStartDate: PropTypes.func.isRequired,
  onChangeEndDate: PropTypes.func.isRequired,
  startDate: PropTypes.instanceOf(Date),
  endDate: PropTypes.instanceOf(Date),
};
