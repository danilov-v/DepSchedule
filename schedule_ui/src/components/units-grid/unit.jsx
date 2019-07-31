import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";

export function Unit({ title, lastGen, lastOfGroup }) {
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
    >
      {title}
    </div>
  );
}

Unit.propTypes = {
  title: PropTypes.string.isRequired,
  lastGen: PropTypes.bool,
};
