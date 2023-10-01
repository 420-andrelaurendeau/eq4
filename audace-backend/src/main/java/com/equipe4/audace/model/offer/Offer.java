package com.equipe4.audace.model.offer;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_gen")
    @SequenceGenerator(name = "offer_gen", sequenceName = "offer_sec", allocationSize = 1)
    @Column(name = "offer_id")
    private Long id;

    private String title;
    private String description;
    private LocalDate internshipStartDate;
    private LocalDate internshipEndDate;
    private LocalDate offerEndDate;
    private int availablePlaces;
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private Department department;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    @ToString.Exclude
    private Employer employer;


    @Builder(builderMethodName = "offerBuilder")
    public Offer(String title, String description, LocalDate internshipStartDate, LocalDate internshipEndDate, LocalDate offerEndDate, int availablePlaces, Department department, Employer employer) {
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.availablePlaces = availablePlaces;
        this.approved = false;
        this.department = department;
        this.employer = employer;
    }

    public boolean isOfferValid() {
        return (title != null && !title.isEmpty()) && (description != null && !description.isEmpty()) && internshipStartDate != null && internshipEndDate != null && offerEndDate != null && employer != null && department != null;
    }

    public boolean isDateValid() {
        return internshipStartDate.isBefore(internshipEndDate) && internshipStartDate.isBefore(offerEndDate);
    }


    /*public OfferDTO toDto() {
        return new OfferDTO(id, title, description, internshipStartDate, internshipEndDate, offerEndDate, employer.getId(), department.toDto());
    }*/
}
