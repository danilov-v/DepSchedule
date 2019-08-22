import React from "react";
import PropTypes from "prop-types";
import {
  Button,
  Form,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "reactstrap";

export const DEFAULT_CONFIRMATION_DATA = {
  open: false,
  title: "",
  body: "",
};

export function ConfirmationDialog({ open, title, body, onSubmit, onClose }) {
  const onApprove = e => {
    e.preventDefault();
    onSubmit();
  };

  return (
    <Modal isOpen={open} toggle={onClose}>
      <Form className="p-3">
        <ModalHeader toggle={onClose}>{title}</ModalHeader>
        <ModalBody>{body}</ModalBody>
        <ModalFooter>
          <Button
            type="submit"
            color="success"
            className="mr-3"
            onClick={onApprove}
          >
            Да, я согласен
          </Button>
          <Button color="primary" onClick={onClose}>
            Отмена
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
}

ConfirmationDialog.propTypes = {
  open: PropTypes.bool,
  title: PropTypes.string,
  body: PropTypes.string,
  onSubmit: PropTypes.func,
  onClose: PropTypes.func,
};

ConfirmationDialog.defaultProps = {
  open: false,
  title: "",
  body: "",
};
