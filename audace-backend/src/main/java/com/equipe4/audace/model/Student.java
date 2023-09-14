package com.equipe4.audace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance
@Data
@NoArgsConstructor
public class Student extends User {
    @Column
    private String studentNumber;

    @ManyToOne
    private Department department;

    public Student(String email, String password, String studentNumber, Department department) {
        super(email, password);
        this.studentNumber = studentNumber;
        this.department = department;
    }

    public Student(Long id, String email, String password, String studentNumber, Department department) {
        super(id, email, password);
        this.studentNumber = studentNumber;
        this.department = department;
    }
}
