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

    public Application fromDTO(){
        return new Application(
                id,
                cv.fromDTO(),
                offer.fromDTO()
        );
    }
}
