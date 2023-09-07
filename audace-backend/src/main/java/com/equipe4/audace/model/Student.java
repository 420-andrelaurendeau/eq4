package com.equipe4.audace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Inheritance
@NoArgsConstructor
public class Student extends User {
    @Column
    private String studentNumber;

    public Student(Long id, String email, String password, String studentNumber) {
        super(id, email, password);
        this.studentNumber = studentNumber;
    }
}
