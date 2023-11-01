package com.equipe4.audace.dto.cv;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.cv.Cv;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CvDTO {
    private Long id;
    private String fileName;
    private byte[] content;
    private Cv.CvStatus cvStatus;
    private StudentDTO student;

    public Cv fromDTO() {
        return new Cv(
                id, fileName, content, student.fromDTO());
    }
}
