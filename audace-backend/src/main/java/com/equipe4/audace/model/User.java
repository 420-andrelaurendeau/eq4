package com.equipe4.audace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUDACE_USER")
public abstract class User {
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="SEQUENCE_USER")
    protected Long id;
    @Column
    protected String email;
    @Column
    protected String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
