package com.equipe4.audace.dto;

import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    //TODO : Spring Validation
    private String studentNumber;
    private DepartmentDTO department;
    private List<Cv> cvs;

    public Student fromDTO() {
        return new Student(
                id,
                firstName,
                lastName,
                email,
                password,
                address,
                phone,
                studentNumber,
                department.fromDto(),
                cvs
        );
    }

    public StudentDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      String address,
                      String phone,
                      String password,
                      String studentNumber,
                      DepartmentDTO department,
                      List<Cv> cvs) {
        super(id, firstName, lastName, address, phone, email, password);
        this.studentNumber = studentNumber;
        this.department = department;
        this.cvs = cvs;
    }
}
