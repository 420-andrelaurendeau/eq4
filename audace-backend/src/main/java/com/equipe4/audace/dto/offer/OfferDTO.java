package com.equipe4.audace.dto.offer;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.OfferStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate internshipStartDate;
    private LocalDate internshipEndDate;
    private LocalDate offerEndDate;
    private int availablePlaces;
    private OfferStatus offerStatus;
    private DepartmentDTO department;
    private EmployerDTO employer;



    public Offer fromDTO() {
        return new Offer(
                id,
                title,
                description,
                internshipStartDate,
                internshipEndDate,
                offerEndDate,
                availablePlaces,
                department.fromDTO(),
                employer.fromDTO()
        );
    }

    public Long getEmployerId() {
        return employer.getId();
    }

    public String getDepartmentCode() {
        return department.getCode();
    }
}
