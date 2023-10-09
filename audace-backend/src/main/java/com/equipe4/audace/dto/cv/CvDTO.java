package com.equipe4.audace.dto.cv;

import com.equipe4.audace.model.cv.Cv;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CvDTO {
    private Long id;
    private String fileName;
    private byte[] content;
    private Long studentId;


    @Builder(builderMethodName = "cvDTOBuilder")
    public CvDTO(Long id, String fileName, byte[] content, Long studentId) {
        this.id = id;
        this.fileName = fileName;
        this.content = content;
        this.studentId = studentId;
    }

    public Cv fromDto() {
        return Cv.cvBuilder()
                .fileName(fileName)
                .content(content)
                .build();
    }
}
