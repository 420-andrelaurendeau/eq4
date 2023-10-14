package com.equipe4.audace.model.cv;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Student;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
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
    private CvStatus cvStatus;

    public enum CvStatus {
        PENDING,
        ACCEPTED,
        REFUSED
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    public Cv(Long id, Student student, byte[] content, String fileName) {
        this.id = id;
        this.student = student;
        this.content = content;
        this.fileName = fileName;
        this.cvStatus = CvStatus.PENDING;
    }

    public CvDTO toDTO() {
        return new CvDTO(
                id,
                fileName,
                content,
                student.toDTO(),
                cvStatus
        );
    }
}
