package com.equipe4.audace.utils;

import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SessionManipulator {
    private final SessionRepository sessionRepository;
    private final OfferSessionRepository offerSessionRepository;

    public Session getCurrentSession() {
        return getSessionAtDate(LocalDate.now());
    }

    public Optional<Session> getSessionById(Long id) {
        return sessionRepository.findById(id);
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

    public List<Offer> removeOffersNotInSession(List<Offer> offers, Long sessionId) {
        List<OfferSession> offerSessions = offerSessionRepository.findAllByOfferInAndSessionId(offers, sessionId);

        return offerSessions
                .stream()
                .map(OfferSession::getOffer)
                .toList();
    }

    public boolean isOfferInCurrentSession(Offer offer) {
        Session session = getSessionAtDate(LocalDate.now());
        return offerSessionRepository.existsByOfferAndSession(offer, session);
    }
}
