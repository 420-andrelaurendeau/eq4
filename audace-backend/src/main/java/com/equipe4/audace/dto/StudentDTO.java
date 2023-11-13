package com.equipe4.audace.dto;

import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    private String studentNumber;
    private DepartmentDTO department;

    public StudentDTO(
            Long id,
            String firstName,
            String lastName,
            String email,
            String address,
            String phone,
            String password,
            String studentNumber,
            DepartmentDTO department
    ) {
        super(id, firstName, lastName, address, phone, email, password);
        this.studentNumber = studentNumber;
        this.department = department;
    }

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
                department.fromDTO()
        );
    }
}
