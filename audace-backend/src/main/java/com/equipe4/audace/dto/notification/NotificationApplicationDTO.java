package com.equipe4.audace.dto.notification;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.model.notification.NotificationApplication;

public class NotificationApplicationDTO extends NotificationDTO {
    public ApplicationDTO content;

    public NotificationApplicationDTO (
            Long id,
            UserDTO userDTO,
            ApplicationDTO content
    ) {
        this.content = content;
    }
    public NotificationApplication fromDTO() {
        return new NotificationApplication(id, userDTO.fromDTO(), content.fromDTO());
    }
}
