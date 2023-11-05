package com.equipe4.audace.dto.notification;

import com.equipe4.audace.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class NotificationDTO {
    protected Long id;
    protected UserDTO userDTO;
}
