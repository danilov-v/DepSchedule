import React from "react";
import PropTypes from "prop-types";

import "./title.scss";

export function Title({ text }) {
  return <div className="title">{text}</div>;
}

Title.propTypes = {
  text: PropTypes.string,
};
