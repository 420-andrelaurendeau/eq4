package com.equipe4.audace.model.notification;

import com.equipe4.audace.dto.notification.NotificationCvDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.cv.Cv;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class NotificationCv extends Notification {
    public NotificationCv(Long id, User user, NotificationCause cause, Cv content) {
        super(id, user, cause);
        this.content = content;
    }
    @ManyToOne
    private Cv content;

    public NotificationCvDTO toDTO() {
        return new NotificationCvDTO(
                id,
                user.toDTO(),
                cause,
                content.toDTO()
        );
    }
}
