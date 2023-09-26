package com.equipe4.audace.model.cv;

import com.equipe4.audace.model.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Cv {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Student uploader;
    private byte[] content;
}
