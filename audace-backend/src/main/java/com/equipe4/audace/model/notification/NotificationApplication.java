package com.equipe4.audace.model.notification;

import com.equipe4.audace.dto.notification.NotificationApplicationDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.application.Application;
import jakarta.persistence.ManyToOne;

public class NotificationApplication extends Notification {
    public NotificationApplication(Long id, User user, NotificationCause cause, Application content) {
        super(id, user, cause);
        this.content = content;
    }
    @ManyToOne
    private Application content;

    public NotificationApplicationDTO toDTO() {
        return new NotificationApplicationDTO(
                id,
                user.toDTO(),
                content.toDTO()
        );
    }
}
