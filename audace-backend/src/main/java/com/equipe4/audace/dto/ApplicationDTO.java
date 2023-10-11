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
    private StudentDTO studentDTO;
    private OfferDTO offerDTO;
    private CvDTO cvDTO;


    //TODO: Modify to add necesary properties for displaying in list
    public Application fromDTO(){
        return new Application(
                id,
                studentDTO.fromDTO(),
                cvDTO.fromDTO(),
                offerDTO.fromDTO()
        );
    }
}
