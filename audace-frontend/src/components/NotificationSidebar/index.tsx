import { useEffect, useState } from 'react';
import './style.css'
import { getNotificationsByUserId } from '../../services/userService';
import { getUserId } from '../../services/authService';
import Notification from '../../model/notification';
import { useTranslation } from 'react-i18next';

const NotificationSidebar = () => {
    const [notifications, setNotifications] = useState<Notification[]>([]);
    const {t} = useTranslation();

    useEffect(() => {
        getNotificationsByUserId(parseInt(getUserId()!))
            .then((res) => {
                console.log(res.data)
                setNotifications(res.data)
            })
    }, [setNotifications]);

    const renderNotifications = () => {
        return notifications.map((notification : Notification) => {
            return (
                <div className="alert alert-danger mb-0 rounded-start-0 notification" key={notification.id}>
                    {makeNotifications(notification)}
                </div>
            )
        })
    }

    const makeNotifications = (notification : Notification) => {
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
                {t("notifications."
                + notification.user.type
                + "." + notification.type
                + "." + notification.cause
                + "1")} {title} {t("notifications."
                + notification.user.type
                + "." + notification.type
                + "." + notification.cause + "2")} {/*TODO : Actually make I18N*/}
            </div>
        )
    }


    return (
        <div className="notification-sidebar col-md-3 col-12 pe-md-3 pb-3 notification-sidebar">
            <h1>Notifications</h1>
            {renderNotifications()}
        </div>
    )
}

export default NotificationSidebar