package com.equipe4.audace.dto;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    //TODO: Modify to add necesary properties for displaying in list
    private Long id;
    private StudentDTO student;
    private OfferDTO offer;
    private CvDTO cv;
    private Application.ApplicationStatus applicationStatus;


    //TODO: Modify to add necesary properties for displaying in list
    public Application fromDTO(){
        return new Application(
                id,
                student.fromDTO(),
                cv.fromDTO(),
                offer.fromDTO()
        );
    }
}
