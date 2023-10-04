package com.equipe4.audace.model;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Student extends User {
    @Column
    private String studentNumber;

    @ManyToOne
    private Department department;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cv> cvs;

    @ToString.Exclude
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

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
        this.cvs = new ArrayList<>();
    }

    public Student(Long id,
                   String firstname,
                   String lastname,
                   String email,
                   String password,
                   String address,
                   String phone,
                   String studentNumber,
                   Department department,
                   List<Cv> cvs) {
        super(id, firstname, lastname, email, password, address, phone);
        this.studentNumber = studentNumber;
        this.department = department;
        this.cvs = cvs;
    }

    @Override
    public StudentDTO toDTO() {
        return new StudentDTO(
                id,
                getFirstName(),
                getLastName(),
                email,
                address,
                phone,
                password,
                studentNumber,
                department.toDTO(),
                cvs.stream().map(Cv::toDto).toList()
        );
    }
}
