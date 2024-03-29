package com.equipe4.audace.model;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.notification.Notification;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_USER")
    @SequenceGenerator(name = "SEQUENCE_USER", sequenceName = "USER_SEC", allocationSize = 1)
    protected Long id;
    protected String firstName;
    protected String lastName;
    @Column(unique = true)
    protected String email;
    @ToString.Exclude
    protected String password;
    protected String address;
    protected String phone;

    public abstract UserDTO toDTO();
}