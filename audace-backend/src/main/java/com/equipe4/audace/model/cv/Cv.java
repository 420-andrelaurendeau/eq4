package com.equipe4.audace.model.cv;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cv_gen")
    @SequenceGenerator(name = "cv_gen", sequenceName = "cv_sec", allocationSize = 1)
    @Column(name = "cv_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private Student student;
    private String fileName;
    private byte[] content;

    @ToString.Exclude
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    @Builder(builderMethodName = "cvBuilder")
    public Cv(Student student, String fileName, byte[] content) {
        this.student = student;
        this.fileName = fileName;
        this.content = content;
    }

    public CvDTO toDTO() {
        return CvDTO.cvDTOBuilder()
                .id(id)
                .fileName(fileName)
                .content(content)
                .studentId(student.getId())
                .build();
    }
}
