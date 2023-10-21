package com.equipe4.audace.repository.offer;

import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.OfferSession;
import com.equipe4.audace.model.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferSessionRepository extends JpaRepository<OfferSession, Long> {
    List<OfferSession> findAllByOfferInAndSession(List<Offer> offers, Session session);
    boolean existsByOfferAndSession(Offer offer, Session session);
}
