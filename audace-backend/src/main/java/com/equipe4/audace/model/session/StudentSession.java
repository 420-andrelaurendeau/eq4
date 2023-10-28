package com.equipe4.audace.model.session;

import com.equipe4.audace.model.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSession {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Student student;

    @ManyToOne
    private Session session;
}
