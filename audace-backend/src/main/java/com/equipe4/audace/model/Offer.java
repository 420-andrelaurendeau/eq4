package com.equipe4.audace.model;

import com.equipe4.audace.dto.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Department;
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
    private Department department;

    @ManyToOne
    private Employer employer;

    public Offer(String title, String description, Date internshipStartDate, Date internshipEndDate, Date offerEndDate, Employer employer, Department department) {
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.employer = employer;
        this.department = department;
    }

    public OfferDTO toDto() {
        return new OfferDTO(id, title, description, internshipStartDate, internshipEndDate, offerEndDate, employer.getId(), department.toDto());
    }
}
