package com.equipe4.audace.dto.offer;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Status status;
    private DepartmentDTO departmentDTO;
    private EmployerDTO employerDTO;


    public Offer fromDTO() {
        return new Offer(
                id,
                title,
                description,
                internshipStartDate,
                internshipEndDate,
                offerEndDate,
                availablePlaces,
                departmentDTO.fromDTO(),
                employerDTO.fromDTO()
        );
    }
}
