package com.equipe4.audace.dto;

import com.equipe4.audace.model.Student;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    //TODO : Spring Validation
    private String studentNumber;
    private String department;

    public Student fromDTO() {
        return new Student(id, email, password, studentNumber, department);
    }

    public StudentDTO(Long id, String email, String password, String studentNumber) {
        super(id, email, password);
        this.studentNumber = studentNumber;
    }
    public StudentDTO(Long id, String email, String password, String studentNumber, String department) {
        super(id, email, password);
        this.studentNumber = studentNumber;
        this.department = department;
    }
}
