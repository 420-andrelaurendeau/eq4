package com.equipe4.audace.model;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToOne;
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

    public Student(Long id,
                   String firstname,
                   String lastname,
                   String email,
                   String password,
                   String address,
                   String phone,
                   String studentNumber,
                   Department department) {
        super(id, firstname, lastname, email, password, address, phone);
        this.studentNumber = studentNumber;
        this.department = department;
    }

    public StudentDTO toDTO() {
        return new StudentDTO(id, getFirstName(), getLastName(), email, address, phone, password, studentNumber, department.toDto());
    }
}
