package com.equipe4.audace.dto.cv;

import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CvDTO {
    private Long id;
    private byte[] content;
    private Long uploaderId;

    public Cv fromDto(Student uploader) {
        return new Cv(id, uploader, content);
    }
}
