import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";

export function Unit({ title, unitId, lastGen, lastOfGroup }) {
  return (
    <div
      className={classnames(
        "unit",
        "d-flex",
        "justify-content-center",
        "align-items-center",
        "text-center",
        { "unit-last-generation": lastGen },
        { "unit-last-of-group": lastOfGroup }
      )}
      data-unit-id={unitId}
    >
      {title}
    </div>
  );
}

Unit.propTypes = {
  title: PropTypes.string.isRequired,
  unitId: PropTypes.number.isRequired,
  lastGen: PropTypes.bool,
};
