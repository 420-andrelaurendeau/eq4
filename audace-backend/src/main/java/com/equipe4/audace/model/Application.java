package com.equipe4.audace.model;

import com.equipe4.audace.dto.ApplicationDTO;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.offer.Offer;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_gen")
    @SequenceGenerator(name = "application_gen", sequenceName = "application_sec", allocationSize = 1)
    @Column(name = "application_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private Student student;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    @ToString.Exclude
    private Cv cv;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    @ToString.Exclude
    private Offer offer;


    @Builder(builderMethodName = "applicationBuilder")
    public Application(Student student, Cv cv, Offer offer) {
        this.student = student;
        this.cv = cv;
        this.offer = offer;
    }

    public ApplicationDTO toDTO(){
        return ApplicationDTO.applicationDTOBuilder()
                .id(id)
                .studentId(student.getId())
                .cvId(cv.getId())
                .offerId(offer.getId())
                .build();
    }
}
