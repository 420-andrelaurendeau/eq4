package com.equipe4.audace.model.notification;

import com.equipe4.audace.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public abstract class Notification<U extends User, T> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_USER")
    @SequenceGenerator(name = "SEQUENCE_USER", sequenceName = "USER_SEC", allocationSize = 1)
    protected Long id;
    protected U user;
    protected T content;
    protected boolean seen;
}
