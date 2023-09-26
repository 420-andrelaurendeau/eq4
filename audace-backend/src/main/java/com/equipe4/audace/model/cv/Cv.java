package com.equipe4.audace.model.cv;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.model.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Cv(Student uploader, String name, byte[] content) {
        this.uploader = uploader;
        this.name = name;
        this.content = content;
    }

    public CvDTO toDto() {
        return new CvDTO(id, name, content, uploader.getId());
    }
}
