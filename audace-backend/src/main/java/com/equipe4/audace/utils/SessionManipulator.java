package com.equipe4.audace.utils;

import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.model.session.StudentSession;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import com.equipe4.audace.repository.session.StudentSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SessionManipulator {
    private final SessionRepository sessionRepository;
    private final OfferSessionRepository offerSessionRepository;
    private final StudentSessionRepository studentSessionRepository;

    public Session getCurrentSession() {
        return getSessionAtDate(LocalDate.now());
    }

    public Session getNextSession(){
        return getSessionAtDate(getCurrentSession().getEndDate().plusWeeks(6));
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

    public List<Offer> removeOffersNotInNextSession(List<Offer> offers, Long sessionId) {
        List<OfferSession> offerSessions = offerSessionRepository.findAllByOfferInAndSessionId(offers, sessionId);

        return offerSessions
                .stream()
                .map(OfferSession::getOffer)
                .toList();
    }

    public boolean isOfferInNextSession(Offer offer) {
        Session session = getNextSession();
        return offerSessionRepository.existsByOfferAndSession(offer, session);
    }

    public List<Cv> removeCvsBelongingToStudentNotInSession(List<Cv> cvs, Long sessionId) {
        List<Student> students = cvs.stream().map(Cv::getStudent).toList();
        List<StudentSession> studentSessions = studentSessionRepository
                .findAllByStudentInAndSessionId(students, sessionId);

        List<Cv> cleanedCvs = new ArrayList<>();

        for (Cv cv : cvs) {
            if (studentSessions
                    .stream()
                    .anyMatch(studentSession -> studentSession
                            .getStudent().getId().equals(cv.getStudent().getId())
                    )
            ) {
                cleanedCvs.add(cv);
            }
        }

        return cleanedCvs;
    }

    public List<Application> removeApplicationsNotInSession(List<Application> applications, Long sessionId) {
        List<Student> students = applications
                .stream()
                .map(Application::getCv)
                .map(Cv::getStudent)
                .toList();

        List<StudentSession> studentSessions = studentSessionRepository
                .findAllByStudentInAndSessionId(students, sessionId);

        List<Application> cleanedApplications = new ArrayList<>();

        for (Application application : applications) {
            Student applicationStudent = application.getCv().getStudent();

            if (studentSessions
                    .stream()
                    .anyMatch(studentSession -> studentSession
                            .getStudent().getId().equals(applicationStudent.getId())
                    )
            ) {
                cleanedApplications.add(application);
            }
        }

        return cleanedApplications;
    }
}
