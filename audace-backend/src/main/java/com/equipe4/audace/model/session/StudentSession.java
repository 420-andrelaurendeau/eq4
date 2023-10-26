package com.equipe4.audace.model.session;

import com.equipe4.audace.model.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class StudentSession {
    @Id
    private Long id;

    private Student student;
    private Session session;
}
