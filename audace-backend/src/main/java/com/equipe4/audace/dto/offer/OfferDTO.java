package com.equipe4.audace.dto.offer;

import com.equipe4.audace.model.offer.Offer;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OfferDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate internshipStartDate;
    private LocalDate internshipEndDate;
    private LocalDate offerEndDate;
    private int availablePlaces;
    private boolean approved;
    private String departmentCode;
    private Long employerId;


    public OfferDTO(Offer offer) {
        this.id = offer.getId();
        this.title = offer.getTitle();
        this.description = offer.getDescription();
        this.internshipStartDate = offer.getInternshipStartDate();
        this.internshipEndDate = offer.getInternshipEndDate();
        this.offerEndDate = offer.getOfferEndDate();
        this.availablePlaces = offer.getAvailablePlaces();
        this.approved = offer.isApproved();
        this.departmentCode = offer.getDepartment().getCode();
        this.employerId = offer.getEmployer().getId();
    }

    @Builder(builderMethodName = "offerDTOBuilder")
    public OfferDTO(Long id, String title, String description, LocalDate internshipStartDate, LocalDate internshipEndDate, LocalDate offerEndDate, int availablePlaces, boolean approved, String departmentCode, Long employerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.availablePlaces = availablePlaces;
        this.approved = approved;
        this.departmentCode = departmentCode;
        this.employerId = employerId;
    }


    public Offer fromDTO() {
        return Offer.offerBuilder()
                .title(title)
                .description(description)
                .internshipStartDate(internshipStartDate)
                .internshipEndDate(internshipEndDate)
                .offerEndDate(offerEndDate)
                .availablePlaces(availablePlaces)
                .build();
    }
}
