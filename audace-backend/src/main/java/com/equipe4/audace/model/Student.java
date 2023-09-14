package com.equipe4.audace.model;

import com.equipe4.audace.dto.StudentDTO;
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
    private String department;

    public Student(String email, String password, String studentNumber) {
        super(email, password);
        this.studentNumber = studentNumber;
    }

    public Student(Long id, String email, String password, String studentNumber) {
        super(id, email, password);
        this.studentNumber = studentNumber;
    }
    public Student(Long id, String email, String password, String studentNumber, String department) {
        super(id, email, password);
        this.studentNumber = studentNumber;
        this.department = department;
    }
    public StudentDTO toDTO() {
        return new StudentDTO(id, email, password, studentNumber, department);
    }
}
