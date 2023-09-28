package com.equipe4.audace.model;

import com.equipe4.audace.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Table(name = "AUDACE_USER") -> Ne permets pas la creation des tables Student et Employer
public abstract class User {
    @Id
    @SequenceGenerator(name = "SEQUENCE_USER", sequenceName = "USER_SEC", allocationSize = 1)
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected String address;
    protected String phone;

    public User(String firstName, String lastName, String email, String password, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public abstract UserDTO toDTO();
}
