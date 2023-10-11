package com.equipe4.audace.model.offer;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public OfferDTO toDTO(){
        return OfferDTO.offerDTOBuilder()
                .id(id)
                .title(title)
                .description(description)
                .internshipStartDate(internshipStartDate)
                .internshipEndDate(internshipEndDate)
                .offerEndDate(offerEndDate)
                .availablePlaces(availablePlaces)
                .status(status)
                .departmentCode(department.getCode())
                .employerId(employer.getId())
                .build();
    }

}
