import React, {
  useState,
  useRef,
  useContext,
  createContext,
  Fragment,
} from "react";
import {
  ConfirmationDialog,
  DEFAULT_CONFIRMATION_DATA,
} from "./confirmation-dialog";

const ConfirmationServiceContext = createContext({});

export const useConfirmation = () => useContext(ConfirmationServiceContext);

export const ConfirmationServiceProvider = ({ children }) => {
  const [confirmationState, setConfirmationState] = useState(
    DEFAULT_CONFIRMATION_DATA
  );

  const awaitingPromiseRef = useRef({
    resolve: () => {},
    reject: () => {},
  });

  const openConfirmation = options => {
    setConfirmationState(options);

    return new Promise((resolve, reject) => {
      awaitingPromiseRef.current = { resolve, reject };
    });
  };

  const handleClose = () => {
    awaitingPromiseRef.current &&
      confirmationState.catchOnCancel &&
      awaitingPromiseRef.current.reject();
    setConfirmationState(null);
  };

  const handleSubmit = () => {
    awaitingPromiseRef.current && awaitingPromiseRef.current.resolve();
    setConfirmationState(null);
  };

  return (
    <Fragment>
      <ConfirmationServiceContext.Provider
        value={openConfirmation}
        children={children}
      />

      <ConfirmationDialog
        open={Boolean(confirmationState)}
        onSubmit={handleSubmit}
        onClose={handleClose}
        {...confirmationState}
      />
    </Fragment>
  );
};
