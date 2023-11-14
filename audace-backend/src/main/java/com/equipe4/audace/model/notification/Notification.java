package com.equipe4.audace.model.notification;

import com.equipe4.audace.dto.notification.NotificationDTO;
import com.equipe4.audace.model.User;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_NOTIFICATION")
    @SequenceGenerator(name = "SEQUENCE_NOTIFICATION", sequenceName = "NOTIFICATION_SEC", allocationSize = 1)
    protected Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
    protected NotificationCause cause;

    public abstract NotificationDTO toDTO();

    public enum NotificationCause {
        CREATED,
        UPDATED,
        DELETED,
        EXPIRED
    }
}
