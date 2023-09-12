package com.equipe4.audace.model.offer;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private Date internshipStartDate;
    private Date internshipEndDate;
    private Date offerEndDate;

    @ManyToOne
    private Employer employer;

    public OfferDTO toDto() {
        return new OfferDTO(id, title, description, internshipStartDate, internshipEndDate, offerEndDate, employer.getId());
    }
}
