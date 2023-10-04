package com.equipe4.audace.dto;

import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationDTO {
    private Long id;
    private Long studentId;
    private Long offerId;
    private Long cvId;

    public ApplicationDTO(Application application) {
        this.id = application.getId();
        this.studentId = application.getStudent().getId();
        this.offerId = application.getOffer().getId();
        this.cvId = application.getCv().getId();
    }

    @Builder(builderMethodName = "applicationDTOBuilder")
    public ApplicationDTO(Long id, Long studentId, Long offerId, Long cvId) {
        this.id = id;
        this.studentId = studentId;
        this.offerId = offerId;
        this.cvId = cvId;
    }

    public Application fromDTO(){
        return Application.applicationBuilder()

                .build();
    }
}
