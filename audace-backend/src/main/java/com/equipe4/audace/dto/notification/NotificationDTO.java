package com.equipe4.audace.dto.notification;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public abstract class NotificationDTO {
    protected Long id;
    protected UserDTO userDTO;
    protected Notification.NotificationCause cause;
}
