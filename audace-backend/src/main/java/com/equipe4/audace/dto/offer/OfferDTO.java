package com.equipe4.audace.dto;

import com.equipe4.audace.model.Employer;
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
    private DepartmentDTO department;

    public OfferDTO(Long id,
                    String title,
                    String description,
                    Date internshipStartDate,
                    Date internshipEndDate,
                    Date offerEndDate,
                    Long employerId,
                    DepartmentDTO department) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.employerId = employerId;
        this.department = department;
    }

    public OfferDTO(Offer offer) {
        this.id = offer.getId();
        this.title = offer.getTitle();
        this.description = offer.getDescription();
        this.internshipStartDate = offer.getInternshipStartDate();
        this.internshipEndDate = offer.getInternshipEndDate();
        this.offerEndDate = offer.getOfferEndDate();
        this.employerId = offer.getEmployer().getId();
        this.department = new DepartmentDTO(offer.getDepartment());
    }

    public Offer fromDto(Employer employer) {
        return new Offer(title, description, internshipStartDate, internshipEndDate, offerEndDate, employer, department.fromDto());
    }
}
