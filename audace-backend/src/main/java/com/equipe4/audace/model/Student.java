package com.equipe4.audace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Inheritance
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
    @Column
    private String email;
    @Column
    private String studentNumber;
    @Column
    private String password;
}
