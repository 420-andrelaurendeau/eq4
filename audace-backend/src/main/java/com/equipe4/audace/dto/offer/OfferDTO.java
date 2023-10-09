package com.equipe4.audace.dto.offer;

import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Status status;
    private String departmentCode;
    private Long employerId;

    @Builder(builderMethodName = "offerDTOBuilder")
    public OfferDTO(Long id, String title, String description, LocalDate internshipStartDate, LocalDate internshipEndDate, LocalDate offerEndDate, int availablePlaces, Status status, String departmentCode, Long employerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.availablePlaces = availablePlaces;
        this.status = status;
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
