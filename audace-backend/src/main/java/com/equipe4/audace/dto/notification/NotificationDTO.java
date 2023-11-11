package com.equipe4.audace.dto.notification;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.notification.NotificationCv;
import com.equipe4.audace.model.notification.NotificationOffer;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NotificationCvDTO.class, name = "cv"),
        @JsonSubTypes.Type(value = NotificationOfferDTO.class, name = "offer"),
        @JsonSubTypes.Type(value = NotificationApplicationDTO.class, name = "application")
})
public abstract class NotificationDTO {
    protected Long id;
    protected UserDTO user;
    protected Notification.NotificationCause cause;
}
