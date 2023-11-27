package com.equipe4.audace.model;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Student extends UserWithDepartment {
    @Column(unique = true)
    private String studentNumber;
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
        super(id, firstname, lastname, email, password, address, phone, department);
        this.studentNumber = studentNumber;
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
