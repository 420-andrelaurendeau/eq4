package com.equipe4.audace.model.offer;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.*;

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
    @Column(length = 2048)
    private String description;
    private LocalDate internshipStartDate;
    private LocalDate internshipEndDate;
    private LocalDate offerEndDate;
    private int availablePlaces;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private Department department;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    @ToString.Exclude
    private Employer employer;

    @ToString.Exclude
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    private Status status;

    public enum Status {
        PENDING,
        ACCEPTED,
        REFUSED
    }

    public Offer(Long id, String title, String description, LocalDate internshipStartDate, LocalDate internshipEndDate, LocalDate offerEndDate, int availablePlaces, Department department, Employer employer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.availablePlaces = availablePlaces;
        this.status = Status.PENDING;
        this.department = department;
        this.employer = employer;
    }


    public OfferDTO toDTO(){
        return new OfferDTO(
                id,
                title,
                description,
                internshipStartDate,
                internshipEndDate,
                offerEndDate,
                availablePlaces,
                status,
                department.toDTO(),
                employer.toDTO()
        );
    }

}
