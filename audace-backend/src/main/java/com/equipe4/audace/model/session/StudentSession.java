package com.equipe4.audace.model.session;

import com.equipe4.audace.model.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class StudentSession {
    @Id
    private Long id;

    @OneToOne
    private Student student;

    @ManyToOne
    private Session session;
}
