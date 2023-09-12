package com.equipe4.audace.dto.offer;

import com.equipe4.audace.model.offer.Offer;
import lombok.Data;

import java.util.Date;

@Data
public class OfferDTO {
    private Long id;
    private String title;
    private String description;
    private Date internshipStartDate;
    private Date internshipEndDate;
    private Date offerEndDate;
    private Long employerId;

    public OfferDTO(Long id, String title, String description, Date internshipStartDate, Date internshipEndDate, Date offerEndDate, Long employerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.employerId = employerId;
    }
}
