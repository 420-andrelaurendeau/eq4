package com.equipe4.audace.model.cv;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cv {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Student uploader;
    private String name;
    private byte[] content;

    @ToString.Exclude
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    public Cv(Student uploader, String name, byte[] content) {
        this.uploader = uploader;
        this.name = name;
        this.content = content;
    }

    public CvDTO toDto() {
        return new CvDTO(id, name, content, uploader.getId());
    }
}
