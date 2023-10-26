package com.equipe4.audace.utils;

import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionManipulatorTest {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private OfferSessionRepository offerSessionRepository;
    @InjectMocks
    private SessionManipulator sessionManipulator;

    @Test
    public void getSessionAtDate_happyPath() {
        Session expected = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));
        LocalDate chosenDate = LocalDate.now().plusMonths(3);

        when(sessionRepository.findAllByDateBetween(chosenDate)).thenReturn(
                List.of(expected)
        );

        Session result = sessionManipulator.getSessionAtDate(chosenDate);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getSessionAtDate_noSessionActive() {
        LocalDate chosenDate = LocalDate.now().plusMonths(3);

        when(sessionRepository.findAllByDateBetween(chosenDate)).thenReturn(
                List.of()
        );

        assertThatThrownBy(() -> sessionManipulator.getSessionAtDate(chosenDate))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No session is currently active");
    }

    @Test
    public void getSessionAtDate_multipleSessionsActive() {
        LocalDate chosenDate = LocalDate.now().plusMonths(3);

        when(sessionRepository.findAllByDateBetween(chosenDate)).thenReturn(
                List.of(
                        new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6)),
                        new Session(2L, LocalDate.now(), LocalDate.now().plusMonths(6))
                )
        );

        assertThatThrownBy(() -> sessionManipulator.getSessionAtDate(chosenDate))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Multiple sessions are currently active");
    }

    @Test
    public void removeOffersNotInSession_happyPath() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));
        List<Offer> expected = List.of(
                new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null),
                new Offer(2L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null)
        );

        List<OfferSession> offerSessions = List.of(
                new OfferSession(1L, expected.get(0), session),
                new OfferSession(2L, expected.get(1), session)
        );

        when(offerSessionRepository.findAllByOfferInAndSessionId(expected, session.getId())).thenReturn(offerSessions);

        List<Offer> result = sessionManipulator.removeOffersNotInSession(expected, session.getId());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void removeOffersNotInSession_noOfferInSession() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));
        List<Offer> expected = List.of(
                new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null),
                new Offer(2L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null)
        );

        when(offerSessionRepository.findAllByOfferInAndSessionId(expected, session.getId())).thenReturn(List.of());

        List<Offer> result = sessionManipulator.removeOffersNotInSession(expected, session.getId());
        assertThat(result).isEqualTo(List.of());
    }

    @Test
    public void removeOffersNotInSession_oneOfferRemoved() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));
        List<Offer> expected = List.of(
                new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null),
                new Offer(2L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null)
        );

        List<OfferSession> offerSessions = List.of(
                new OfferSession(1L, expected.get(0), session)
        );

        when(offerSessionRepository.findAllByOfferInAndSessionId(expected, session.getId())).thenReturn(offerSessions);

        List<Offer> result = sessionManipulator.removeOffersNotInSession(expected, session.getId());
        assertThat(result).isEqualTo(List.of(expected.get(0)));
    }

    @Test
    public void isOfferInCurrentSession_happyPath() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));

        Offer offer = new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null);

        when(sessionRepository.findAllByDateBetween(any())).thenReturn(
                List.of(session)
        );
        when(offerSessionRepository.existsByOfferAndSession(offer, session)).thenReturn(true);

        boolean result = sessionManipulator.isOfferInCurrentSession(offer);
        assertThat(result).isTrue();
    }

    @Test
    public void isOfferInCurrentSession_offerNotInSession() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));

        Offer offer = new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null);

        when(sessionRepository.findAllByDateBetween(any())).thenReturn(
                List.of(session)
        );
        when(offerSessionRepository.existsByOfferAndSession(offer, session)).thenReturn(false);

        boolean result = sessionManipulator.isOfferInCurrentSession(offer);
        assertThat(result).isFalse();
    }

    @Test
    public void removeOffersNotInCurrentSession_happyPath() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));

        List<Offer> expected = List.of(
                new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null),
                new Offer(2L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, null, null)
        );

        List<OfferSession> offerSessions = List.of(
                new OfferSession(1L, expected.get(0), session),
                new OfferSession(2L, expected.get(1), session)
        );

        when(offerSessionRepository.findAllByOfferInAndSessionId(expected, session.getId())).thenReturn(offerSessions);

        List<Offer> result = sessionManipulator.removeOffersNotInSession(expected, session.getId());

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getCurrentSessionTest() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));
        when(sessionRepository.findAllByDateBetween(any())).thenReturn(
                List.of(session)
        );

        Session result = sessionManipulator.getCurrentSession();

        assertThat(result).isEqualTo(session);
    }

    @Test
    public void getSessionByIdTest() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));
        when(sessionRepository.findById(1L)).thenReturn(
                java.util.Optional.of(session)
        );

        Session result = sessionManipulator.getSessionById(1L).get();

        assertThat(result).isEqualTo(session);
    }
}
