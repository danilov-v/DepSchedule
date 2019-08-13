import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { Toast, ToastBody, ToastHeader } from "reactstrap";
import { NotificationManager } from "helpers/notification-manager";

import "./notification.scss";

const DEFAULT_NOTIFICATION_DATA = {
  isOpen: false,
  title: "",
  body: "",
  color: "primary",
};

export function Notification({ data }) {
  const [notificationData, setNotificationData] = useState(data);

  useEffect(() => {
    NotificationManager.subscribe(data => {
      setNotificationData(data);

      setTimeout(() => {
        setNotificationData(DEFAULT_NOTIFICATION_DATA);
      }, 3000);
    });
  }, []);

  return (
    <div className="notification">
      <div className="p-3 my-2 rounded">
        <Toast isOpen={notificationData.isOpen}>
          <ToastHeader icon={notificationData.color}>
            {notificationData.title}
          </ToastHeader>
          <ToastBody>{notificationData.body}</ToastBody>
        </Toast>
      </div>
    </div>
  );
}

Notification.propTypes = {
  data: PropTypes.shape({
    isOpen: PropTypes.bool,
    title: PropTypes.string,
    body: PropTypes.string,
  }),
};

Notification.defaultProps = {
  data: DEFAULT_NOTIFICATION_DATA,
};
