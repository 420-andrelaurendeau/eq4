package com.equipe4.audace.dto;

import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.Student;
import lombok.*;

@Data
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    //TODO : Spring Validation
    private String studentNumber;
    private DepartmentDTO departmentDTO;

    @Builder(builderMethodName = "studentDTOBuilder")
    public StudentDTO(Long id, String firstName, String lastName, String email, String address, String phone, String password, String studentNumber, DepartmentDTO departmentDTO) {
        super(id, firstName, lastName, address, phone, email, password);
        this.studentNumber = studentNumber;
        this.departmentDTO = departmentDTO;
    }

    public Student fromDTO() {
        return Student.studentBuilder()
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .password(password)
                .address(address)
                .phone(phone)
                .studentNumber(studentNumber)
                .department(departmentDTO.fromDto())
                .build();
    }

}
