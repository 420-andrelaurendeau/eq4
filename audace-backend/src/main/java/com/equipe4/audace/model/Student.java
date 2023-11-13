package com.equipe4.audace.model;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@ToString(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Student extends User {
    @Column(unique = true)
    private String studentNumber;
    @ManyToOne
    private Department department;
    @ToString.Exclude
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Cv> cv = new ArrayList<>();

    public Student(
            Long id,
            String firstname,
            String lastname,
            String email,
            String password,
            String address,
            String phone,
            String studentNumber,
            Department department
    ) {
        super(id, firstname, lastname, email, password, address, phone);
        this.studentNumber = studentNumber;
        this.department = department;
    }

    @Override
    public StudentDTO toDTO() {
        return new StudentDTO(
                id,
                firstName,
                lastName,
                email,
                address,
                phone,
                password,
                studentNumber,
                department.toDTO()
        );
    }
}
