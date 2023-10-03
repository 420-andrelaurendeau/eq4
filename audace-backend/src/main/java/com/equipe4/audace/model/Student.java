package com.equipe4.audace.model;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Data
@NoArgsConstructor
public class Student extends User {
    @Column
    private String studentNumber;

    @ManyToOne
    private Department department;

    public Student(Long id, String firstname, String lastname, String email, String password, String address, String phone, String studentNumber, Department department) {
        super(id, firstname, lastname, email, password, address, phone);
        this.studentNumber = studentNumber;
        this.department = department;
    }

    @Override
    public StudentDTO toDTO() {
        return new StudentDTO(this.id, this.firstName, this.lastName, this.email, this.address, this.phone, this.password, "student", this.studentNumber, this.department.toDTO());
    }
}
