package com.equipe4.audace.model.offer;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Column(length = 2048)
    private String description;
    private Date internshipStartDate;
    private Date internshipEndDate;
    private Date offerEndDate;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Employer employer;

    private Status status;

    public enum Status {
        PENDING,
        ACCEPTED,
        REFUSED
    }


    public Offer(String title, String description, Date internshipStartDate, Date internshipEndDate, Date offerEndDate, Employer employer, Department department) {
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.employer = employer;
        this.department = department;
        status = Status.PENDING;
    }

    public OfferDTO toDTO() {
        return new OfferDTO(id, title, description, internshipStartDate, internshipEndDate, offerEndDate, employer.getId(), department.toDTO(), status);
    }
}
