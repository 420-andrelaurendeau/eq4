package com.equipe4.audace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="SEQUENCE_USER")
    @SequenceGenerator(name = "SEQUENCE_USER", sequenceName = "USER_SEC", allocationSize = 1)
    protected Long id;
    private String firstName;
    private String lastName;
    protected String email;
    protected String password;
    private String address;
    private String phone;

    public User(String firstName, String lastName, String email, String password, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public User(Long id, String email, String password) {
    }
}
