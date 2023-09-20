package com.equipe4.audace.model;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Inheritance
@Data
@NoArgsConstructor
public class Student extends User {
    @Column
    private String studentNumber;

    @ManyToOne(cascade = CascadeType.ALL)
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

    public StudentDTO toDTO() {
        return new StudentDTO(id, email, password, studentNumber, department.toDto());
    }
}
