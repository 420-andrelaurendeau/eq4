package com.equipe4.audace.repository.session;

import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferSessionRepository extends JpaRepository<OfferSession, Long> {
    List<OfferSession> findAllByOfferInAndSessionId(List<Offer> offers, Long sessionId);
    boolean existsByOfferAndSession(Offer offer, Session session);
}
