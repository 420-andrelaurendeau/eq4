package com.equipe4.audace.utils;

import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.notification.NotificationCv;
import com.equipe4.audace.model.notification.NotificationOffer;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.notification.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class NotificationManipulator {
    private final NotificationRepository notificationRepository;
    private final StudentRepository studentRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public void makeNotificationOfferToAllStudents(Offer offer, Notification.NotificationCause cause) {
        nullCheck(offer);
        nullCheck(cause);
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            notificationRepository.save(new NotificationOffer(null, student, cause, offer));
        }
    }

    @Transactional
    public void makeNotificationOfferToAllManagers(Offer offer, Notification.NotificationCause cause) {
        nullCheck(offer);
        nullCheck(cause);
        List<Manager> managers = managerRepository.findAll();
        for (Manager manager : managers) {
            notificationRepository.save(new NotificationOffer(null, manager, cause, offer));
        }
    }

    public void makeNotificationCvToCvStudent(Cv cv, Notification.NotificationCause cause) {
        nullCheck(cv);
        nullCheck(cause);
        notificationRepository.save(new NotificationCv(null, cv.getStudent(), cause, cv));
    }

    public void makeNotificationOfferToOfferEmployer(Offer offer, Notification.NotificationCause cause) {
        nullCheck(offer);
        nullCheck(cause);
        notificationRepository.save(new NotificationOffer(null, offer.getEmployer(), cause, offer));
    }

    public void makeNotificationApplicationToOfferEmployer(Application application, Notification.NotificationCause cause) {
        nullCheck(application);
        nullCheck(cause);
        notificationRepository.save(new NotificationOffer(null, application.getOffer().getEmployer(), cause, application.getOffer()));
    }

    public void makeNotificationApplicationToStudent(Application application, Notification.NotificationCause cause) {
        nullCheck(application);
        nullCheck(cause);
        notificationRepository.save(new NotificationOffer(null, application.getCv().getStudent(), cause, application.getOffer()));
    }

    @Transactional
    public void makeNotificationCvToAllManagersByDepartment(Cv cv, Notification.NotificationCause cause) {
        nullCheck(cv);
        nullCheck(cause);
        List<Manager> managers = managerRepository.findManagersByDepartmentId(cv.getStudent().getDepartment().getId());
        for (Manager manager : managers) {
            notificationRepository.save(new NotificationCv(null, manager, cause, cv));
        }
    }

    public List<Notification> getAllNotificationsByUserId(Long id) {
        nullCheck(id);
        return notificationRepository.findAllByUserId(id);
    }

    public void deleteAllNotificationsByUserId(Long userId) {
        nullCheck(userId);
        notificationRepository.deleteAllByUserId(userId);
    }

    private <T> void nullCheck(T object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
    public void deleteNotificationById(Long id) { //TODO : Tests
        nullCheck(id);
        notificationRepository.deleteById(id);
    }
}
