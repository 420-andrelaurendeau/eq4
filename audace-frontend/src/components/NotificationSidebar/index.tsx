import { useEffect, useState } from "react";
import "./style.css";
import {
  deleteNotificationById,
  getNotificationsByUserId,
  deleteAllNotificationsByUserId,
} from "../../services/notificationService";
import { getUserId } from "../../services/authService";
import Notification from "../../model/notification";
import { useTranslation } from "react-i18next";
import { Button, Container, Row } from "react-bootstrap";

const NotificationSidebar = () => {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const { t } = useTranslation();
  const userId = parseInt(getUserId()!);

  useEffect(() => {
    getNotificationsByUserId(userId).then((res) => {
      setNotifications(res.data);
    });
  }, [userId, setNotifications]);

  const deleteNotification = (notification: Notification) => {
    deleteNotificationById(notification.id!);
    setNotifications(notifications.filter((n) => n.id !== notification.id));
  };

  const deleteAllNotifications = () => {
    deleteAllNotificationsByUserId(userId);
    setNotifications([]);
  };

  const renderNotifications = () => {
    return notifications.map((notification: Notification) => {
      return (
        <Container key={notification.id} className="alert alert-info mb-2 rounded-start-0 notification">
          <div
            className="float-end cursor-pointer"
            onClick={() => {
              deleteNotification(notification);
            }}
          >
            <i className="bi bi-trash"></i>
          </div>
          {makeNotifications(notification)}
        </Container>
      );
    });
  };

  const makeNotifications = (notification: Notification) => {
    let title = "";
    switch (notification.type) {
      case "offer":
        title = notification.offer!.title;
        break;
      case "cv":
        title = notification.cv!.fileName;
        break;
      case "application":
        title = notification.application!.offer!.title;
        break;
    }
    return (
      <div>
        {t(
          "notifications." +
          notification.user.type +
          "." +
          notification.type +
          "." +
          notification.cause +
          "1"
        )}{" "}
        {title}{" "}
        {t(
          "notifications." +
          notification.user.type +
          "." +
          notification.type +
          "." +
          notification.cause +
          "2"
        )}
      </div>
    );
  };

  return (
    <div className="notification-sidebar pe-md-3 pb-3">
      <div className="ms-2">
        <h2>Notifications</h2> {/*TODO : I18N for the future chinese peeps*/}
        <Button
          variant="outline-danger"
          className="mb-2 p-1 text-dark"
          onClick={deleteAllNotifications}
        >
          {t("notifications.deleteAll")}
        </Button>
      </div>
      {renderNotifications()}
    </div>
  );
};

export default NotificationSidebar;
