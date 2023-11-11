package com.equipe4.audace.utils;

import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.notification.NotificationOffer;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.notification.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationManipulatorTest {
    @Mock
    NotificationRepository notificationRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    ManagerRepository managerRepository;
    @InjectMocks
    NotificationManipulator notificationManipulator;

    @Test
    public void makeNotificationOfferToAllStudents_happyPath() {
        Student student = new Student();
        Student student2 = new Student();

        when(studentRepository.findAll()).thenReturn(
                List.of(student, student2)
        );
        when(notificationRepository.save(any())).thenReturn(null);

        notificationManipulator.makeNotificationOfferToAllStudents(mock(Offer.class), Notification.NotificationCause.CREATED);

        verify(notificationRepository, times(2)).save(any());
    }
    @Test
    public void makeNotificationOfferToAllStudents_nullOffer() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationOfferToAllStudents(null, Notification.NotificationCause.UPDATED))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationOfferToAllStudents_nullCause() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationOfferToAllStudents(mock(Offer.class), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationOfferToAllManagers_happyPath() {
        Manager manager = new Manager();
        Manager manager2 = new Manager();

        when(managerRepository.findAll()).thenReturn(
                List.of(manager, manager2)
        );
        when(notificationRepository.save(any())).thenReturn(null);

        notificationManipulator.makeNotificationOfferToAllManagers(mock(Offer.class), Notification.NotificationCause.CREATED);

        verify(notificationRepository, times(2)).save(any());
    }
    @Test
    public void makeNotificationOfferToAllManagers_nullOffer() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationOfferToAllManagers(null, Notification.NotificationCause.UPDATED))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationOfferToAllManagers_nullCause() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationOfferToAllManagers(mock(Offer.class), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationCvToCvStudent_happyPath() {
        Cv cv = mock(Cv.class);
        Student student = mock(Student.class);

        when(cv.getStudent()).thenReturn(student);
        when(notificationRepository.save(any())).thenReturn(null);

        notificationManipulator.makeNotificationCvToCvStudent(cv, Notification.NotificationCause.CREATED);

        verify(notificationRepository, times(1)).save(any());
    }
    @Test
    public void makeNotificationCvToCvStudent_nullCv() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationCvToCvStudent(null, Notification.NotificationCause.UPDATED))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationCvToCvStudent_nullCause() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationCvToCvStudent(mock(Cv.class), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationOfferToOfferEmployer_happyPath() {
        Offer offer = mock(Offer.class);
        Employer employer = mock(Employer.class);

        when(offer.getEmployer()).thenReturn(employer);
        when(notificationRepository.save(any())).thenReturn(null);

        notificationManipulator.makeNotificationOfferToOfferEmployer(offer, Notification.NotificationCause.CREATED);

        verify(notificationRepository, times(1)).save(any());
    }
    @Test
    public void makeNotificationOfferToOfferEmployer_nullOffer() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationOfferToOfferEmployer(null, Notification.NotificationCause.UPDATED))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationOfferToOfferEmployer_nullCause() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationOfferToOfferEmployer(mock(Offer.class), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationApplicationToOfferEmployer_happyPath() {
        Application application = mock(Application.class);
        Offer offer = mock(Offer.class);
        Employer employer = mock(Employer.class);

        when(application.getOffer()).thenReturn(offer);
        when(offer.getEmployer()).thenReturn(employer);
        when(notificationRepository.save(any())).thenReturn(null);

        notificationManipulator.makeNotificationApplicationToOfferEmployer(application, Notification.NotificationCause.CREATED);

        verify(notificationRepository, times(1)).save(any());
    }
    @Test
    public void makeNotificationApplicationToOfferEmployer_nullApplication() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationApplicationToOfferEmployer(null, Notification.NotificationCause.UPDATED))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationApplicationToOfferEmployer_nullCause() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationApplicationToOfferEmployer(mock(Application.class), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationApplicationToStudent_happyPath() {
        Application application = mock(Application.class);
        Cv cv = mock(Cv.class);
        Student student = mock(Student.class);

        when(application.getCv()).thenReturn(cv);
        when(cv.getStudent()).thenReturn(student);
        when(notificationRepository.save(any())).thenReturn(null);

        notificationManipulator.makeNotificationApplicationToStudent(application, Notification.NotificationCause.CREATED);

        verify(notificationRepository, times(1)).save(any());
    }
    @Test
    public void makeNotificationApplicationToStudent_nullApplication() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationApplicationToStudent(null, Notification.NotificationCause.UPDATED))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationApplicationToStudent_nullCause() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationApplicationToStudent(mock(Application.class), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationCvToAllManagersByDepartment_happyPath() {
        Cv cv = mock(Cv.class);
        Student student = mock(Student.class);
        Manager manager = mock(Manager.class);

        when(cv.getStudent()).thenReturn(student);
        when(student.getDepartment()).thenReturn(mock());
        when(managerRepository.findManagersByDepartmentId(any())).thenReturn(List.of(manager));
        when(notificationRepository.save(any())).thenReturn(null);

        notificationManipulator.makeNotificationCvToAllManagersByDepartment(cv, Notification.NotificationCause.CREATED);

        verify(notificationRepository, times(1)).save(any());
    }
    @Test
    public void makeNotificationCvToAllManagersByDepartment_nullCv() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationCvToAllManagersByDepartment(null, Notification.NotificationCause.UPDATED))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeNotificationCvToAllManagersByDepartment_nullCause() {
        assertThatThrownBy(() -> notificationManipulator.makeNotificationCvToAllManagersByDepartment(mock(Cv.class), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void getAllNotificationsByUserId_happyPath() {
        Student student = mock(Student.class);
        when(notificationRepository.findAllByUserId(1L)).thenReturn(List.of(
                new NotificationOffer(1L, student, Notification.NotificationCause.CREATED, mock(Offer.class)),
                new NotificationOffer(2L, student, Notification.NotificationCause.UPDATED, mock(Offer.class))
        ));
        List<Notification> notifications = notificationManipulator.getAllNotificationsByUserId(1L);
        assertThat(notifications).hasSize(2);
        assertThat(notifications.get(0).getId()).isEqualTo(1L);
        assertThat(notifications.get(1).getId()).isEqualTo(2L);
    }
    @Test
    public void getAllNotificationsByUserId_nullId() {
        assertThatThrownBy(() -> notificationManipulator.getAllNotificationsByUserId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void deleteAllByUserId_happyPath() {
        notificationManipulator.deleteAllNotificationsByUserId(1L);
        verify(notificationRepository, times(1)).deleteAllByUserId(1L);
    }
    @Test
    public void deleteAllByUserId_nullId() {
        assertThatThrownBy(() -> notificationManipulator.deleteAllNotificationsByUserId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void hasNotificationByUserId_happyPath() {
        when(notificationRepository.existsByUserId(1L)).thenReturn(true);
        boolean hasNotification = notificationManipulator.hasNotificationByUserId(1L);
        assertThat(hasNotification).isTrue();
    }
    @Test
    public void hasNotificationByUserId_nullId() {
        assertThatThrownBy(() -> notificationManipulator.hasNotificationByUserId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void deleteNotificationById_happyPath() {
        notificationManipulator.deleteNotificationById(1L);
        verify(notificationRepository, times(1)).deleteById(1L);
    }
    @Test
    public void deleteNotificationById_nullId() {
        assertThatThrownBy(() -> notificationManipulator.deleteNotificationById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
