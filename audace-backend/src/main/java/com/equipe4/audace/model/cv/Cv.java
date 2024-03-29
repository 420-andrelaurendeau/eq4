package com.equipe4.audace.model.cv;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cv_gen")
    @SequenceGenerator(name = "cv_gen", sequenceName = "cv_sec", allocationSize = 1)
    @Column(name = "cv_id")
    private Long id;
    private String fileName;
    private byte[] content;
    private CvStatus cvStatus;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private Student student;

    @ToString.Exclude
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    public enum CvStatus {
        PENDING,
        ACCEPTED,
        REFUSED
    }

    public Cv(Long id, String fileName, byte[] content, Student student) {
        this.id = id;
        this.fileName = fileName;
        this.content = content;
        this.cvStatus = CvStatus.PENDING;
        this.student = student;
    }

    public CvDTO toDTO() {
        return new CvDTO(id, fileName, content,  cvStatus, student.toDTO());
    }
}
