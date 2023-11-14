package com.equipe4.audace.dto.notification;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.notification.NotificationApplication;

public class NotificationApplicationDTO extends NotificationDTO {
    public ApplicationDTO application;

    public NotificationApplicationDTO (
            Long id,
            UserDTO userDTO,
            Notification.NotificationCause cause,
            ApplicationDTO content
    ) {
        super(id, userDTO, cause);
        this.application = content;
    }
    public NotificationApplication fromDTO() {
        return new NotificationApplication(id, user.fromDTO(), cause, application.fromDTO());
    }
}
