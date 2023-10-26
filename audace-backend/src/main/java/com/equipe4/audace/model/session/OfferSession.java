package com.equipe4.audace.model.session;

import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.session.Session;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferSession {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Offer offer;

    @ManyToOne
    private Session session;
}
