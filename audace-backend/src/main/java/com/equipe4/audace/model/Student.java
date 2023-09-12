package com.equipe4.audace.model;

import com.equipe4.audace.model.department.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@Inheritance
@NoArgsConstructor
public class Student extends User {
    @Column
    private String studentNumber;

    @ManyToOne
    private Department department;

    public Student(Long id, String email, String password, String studentNumber, Department department) {
        super(id, email, password);
        this.studentNumber = studentNumber;
        this.department = department;
    }
}
