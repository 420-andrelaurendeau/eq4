package com.equipe4.audace.dto;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    //TODO : Spring Validation
    private String studentNumber;
    private DepartmentDTO department;
    private List<CvDTO> cvs;

    public Student fromDTO() {
        Student student = new Student(
                id,
                firstName,
                lastName,
                email,
                password,
                address,
                phone,
                studentNumber,
                department.fromDto()
        );

        student.setCvs(cvs.stream().map(cvDTO -> cvDTO.fromDto(student)).toList());
        return student;
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
                      List<CvDTO> cvs) {
        super(id, firstName, lastName, address, phone, email, password);
        this.studentNumber = studentNumber;
        this.department = department;
        this.cvs = cvs;
    }
}
