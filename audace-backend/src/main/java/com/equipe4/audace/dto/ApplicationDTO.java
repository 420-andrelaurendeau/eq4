package com.equipe4.audace.dto;

import com.equipe4.audace.model.Application;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationDTO {
    //TODO: Modify to add necesary properties for displaying in list
    private Long id;
    private Long studentId;
    private Long offerId;
    private Long cvId;


    @Builder(builderMethodName = "applicationDTOBuilder")
    public ApplicationDTO(Long id, Long studentId, Long offerId, Long cvId) {
        this.id = id;
        this.studentId = studentId;
        this.offerId = offerId;
        this.cvId = cvId;
    }

    //TODO: Modify to add necesary properties for displaying in list
    public Application fromDTO(){
        return Application.applicationBuilder()
                .build();
    }
}
