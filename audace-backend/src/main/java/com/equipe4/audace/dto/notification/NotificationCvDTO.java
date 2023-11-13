package com.equipe4.audace.dto.notification;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.notification.NotificationCv;

public class NotificationCvDTO extends NotificationDTO {
    public CvDTO cv;

    public NotificationCvDTO(
            Long id,
            UserDTO userDTO,
            Notification.NotificationCause cause,
            CvDTO content
    ) {
        super(id, userDTO, cause);
        this.cv = content;
    }

    public NotificationCv fromDTO() {
        return new NotificationCv(id, user.fromDTO(), cause, cv.fromDTO());
    }
}