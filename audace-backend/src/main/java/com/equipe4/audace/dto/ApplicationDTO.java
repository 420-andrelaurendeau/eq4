package com.equipe4.audace.dto;

import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.cv.Cv;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationDTO {
    private Long id;
    private String studentFirstName;
    private String studentLastName;
    private String departmentName;
    private Long offerId;
    private Cv cv;


    public ApplicationDTO(Application application) {
        this.id = application.getId();
        this.studentFirstName = application.getStudent().getFirstName();
        this.studentLastName = application.getStudent().getLastName();
        this.departmentName = application.getStudent().getDepartment().getName();
        this.offerId = application.getOffer().getId();
        this.cv = application.getCv();
    }

    @Builder(builderMethodName = "applicationDTOBuilder")
    public ApplicationDTO(Long id, String studentFirstName, String studentLastName, String departmentName, Long offerId, Cv cv) {
        this.id = id;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.departmentName = departmentName;
        this.offerId = offerId;
        this.cv = cv;
    }

    public Application fromDTO(){
        return Application.applicationBuilder()
                .cv(cv)
                .build();
    }
}
