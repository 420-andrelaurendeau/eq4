package com.equipe4.audace.dto;

import com.equipe4.audace.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    //TODO : Spring Validation
    private String studentNumber;
    private DepartmentDTO department;

    public Student fromDTO() {
        return new Student(id, email, password, studentNumber, department.fromDto());
    }

    public StudentDTO(Long id, String email, String password, String studentNumber, DepartmentDTO department) {
        super(id, email, password);
        this.studentNumber = studentNumber;
        this.department = department;
    }
}
