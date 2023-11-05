package com.equipe4.audace.dto.notification;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.model.notification.NotificationCv;

public class NotificationCvDTO extends NotificationDTO {
    public CvDTO content;

    public NotificationCvDTO(
            Long id,
            UserDTO userDTO,
            CvDTO content
    ) {
        super(id, userDTO);
        this.content = content;
    }

    public NotificationCv fromDTO() {
        return new NotificationCv(id, userDTO.fromDTO(), content.fromDTO());
    }
}