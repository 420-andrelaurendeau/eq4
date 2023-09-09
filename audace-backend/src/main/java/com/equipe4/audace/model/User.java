package com.equipe4.audace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Table(name = "AUDACE_USER") -> Ne permets pas la creation des tables Student et Employer
public abstract class User {
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="SEQUENCE_USER")
    @SequenceGenerator(name = "SEQUENCE_USER", sequenceName = "USER_SEC", allocationSize = 1)
    protected Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    protected String email;
    @Column
    protected String password;
    @Column
    private String address;
    @Column
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
