import React, { useRef, useEffect } from "react";
import PropTypes from "prop-types";

export function OutsideListener({ children, callback }) {
  const wrapperRef = useRef(null);

  const handleClickOutside = event => {
    if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
      callback();
    }
  };

  useEffect(() => {
    // Bind the event listener
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      // Unbind the event listener on clean up
      document.removeEventListener("mousedown", handleClickOutside);
    };
  });

  return <div ref={wrapperRef}>{children}</div>;
}

OutsideListener.propTypes = {
  children: PropTypes.element.isRequired,
  callback: PropTypes.func.isRequired,
};
