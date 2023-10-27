package com.equipe4.audace.model.application;

import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.offer.Offer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JoinColumn(name = "cv_id")
    @ToString.Exclude
    private Cv cv;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    @ToString.Exclude
    private Offer offer;

    public ApplicationDTO toDTO(){
        return new ApplicationDTO(
                id,
                offer.toDTO(),
                cv.toDTO()
        );
    }
}