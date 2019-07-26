import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";

export function Unit({ title, lastGen }) {
  return (
    <div
      className={classnames(
        "unit",
        "d-flex",
        "justify-content-center",
        "align-items-center",
        "text-center",
        { "unit-last-generation": lastGen }
      )}
    >
      {title}
    </div>
  );
}

Unit.propTypes = {
  title: PropTypes.string.isRequired,
  lastGen: PropTypes.bool,
};
