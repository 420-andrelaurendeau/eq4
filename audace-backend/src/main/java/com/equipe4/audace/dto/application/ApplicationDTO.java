package com.equipe4.audace.dto.application;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.application.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    private Long id;
    private OfferDTO offer;
    private CvDTO cv;
    private Application.ApplicationStatus applicationStatus;

    private ApplicationDTO(Long id, OfferDTO offer, CvDTO cv){
        this.id = id;
        this.offer = offer;
        this.cv = cv;
        this.applicationStatus = Application.ApplicationStatus.PENDING;
    }

    public Application fromDTO(){
        return new Application(
                id,
                cv.fromDTO(),
                offer.fromDTO(),
                applicationStatus
        );
    }
}
