package com.equipe4.audace.utils;

import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.offer.OfferSessionRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class SessionManipulator {
    private final SessionRepository sessionRepository;
    private final OfferSessionRepository offerSessionRepository;

    public Session getCurrentSession() {
        return getSessionAtDate(LocalDate.now());
    }

    public Session getSessionAtDate(LocalDate chosenDate) {
        List<Session> sessions = sessionRepository.findAllByDateBetween(chosenDate);

        if (sessions.isEmpty()) {
            throw new IllegalStateException("No session is currently active");
        }

        if (sessions.size() > 1) {
            throw new IllegalStateException("Multiple sessions are currently active");
        }

        return sessions.get(0);
    }

    public List<Offer> removeOffersNotInCurrentSession(List<Offer> offers) {
        Session session = getCurrentSession();

        return removeOffersNotInSession(offers, session);
    }

    public List<Offer> removeOffersNotInSession(List<Offer> offers, Session session) {
        List<OfferSession> offerSessions = offerSessionRepository.findAllByOfferInAndSession(offers, session);

        return offerSessions
                .stream()
                .map(OfferSession::getOffer)
                .toList();
    }

    public boolean isOfferInCurrentSession(Offer offer) {
        Session session = getCurrentSession();

        return offerSessionRepository.existsByOfferAndSession(offer, session);
    }
}
