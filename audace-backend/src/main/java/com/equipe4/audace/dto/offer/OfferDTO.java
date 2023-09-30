package com.equipe4.audace.dto.offer;

import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.offer.Offer;
import lombok.Data;
import com.equipe4.audace.model.offer.Offer.Status;
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
    private Status status;

    public OfferDTO(Long id,
                    String title,
                    String description,
                    Date internshipStartDate,
                    Date internshipEndDate,
                    Date offerEndDate,
                    Long employerId,
                    DepartmentDTO department,
                    Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.offerEndDate = offerEndDate;
        this.employerId = employerId;
        this.department = department;
        this.status = status;
    }

    public Offer fromDto(Employer employer) {
        return new Offer(id, title, description, internshipStartDate, internshipEndDate, offerEndDate, department.toDto(), employer, status);
    }
}
