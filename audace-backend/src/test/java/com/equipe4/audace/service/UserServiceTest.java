package com.equipe4.audace.service;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.notification.NotificationDTO;
import com.equipe4.audace.dto.session.SessionDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.notification.NotificationRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import com.equipe4.audace.utils.NotificationManipulator;
import com.equipe4.audace.utils.SessionManipulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private SessionManipulator sessionManipulator;
    @Mock
    private NotificationManipulator notificationManipulator;
    @InjectMocks
    private UserService userService;

    @Test
    void testGetUser() {
        long userId = 3L;

        Department department = new Department(1L, "department", "department");
        User user = new Student(userId, "student", "studentman", "student@email.com", "password", "123 Street Street", "1234567890", "123456789", department);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        Optional<UserDTO> userDTO = userService.getUser(userId);

        assertTrue(userDTO.isPresent());
    }

    @Test
    void testGetUserNotFound() {
        long userId = 3L;

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Optional<UserDTO> userDTO = userService.getUser(userId);

        assertTrue(userDTO.isEmpty());
    }

    @Test
    void getSessions_happyPath_test() {
        List<Session> sessions = List.of(
                new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(1)),
                new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(1))
        );

        when(sessionRepository.findAll()).thenReturn(sessions);

        List<SessionDTO> sessionDTOs = userService.getSessions();

        assertThat(sessionDTOs).hasSize(2);
        assertThat(sessionDTOs).containsExactlyInAnyOrderElementsOf(sessions.stream().map(Session::toDTO).toList());
    }

    @Test
    void getCurrentSession_happyPath_test() {
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(1));

        when(sessionManipulator.getNextSession()).thenReturn(session);

        Optional<SessionDTO> sessionDTO = userService.getNextSession();

        assertTrue(sessionDTO.isPresent());
        assertThat(sessionDTO.get()).isEqualTo(session.toDTO());
    }

    @Test
    void getSession_happyPath_test() {
        long sessionId = 1L;
        Session session = new Session(sessionId, LocalDate.now(), LocalDate.now().plusMonths(1));

        when(sessionRepository.findById(any())).thenReturn(Optional.of(session));

        Optional<SessionDTO> sessionDTO = userService.getSessionById(sessionId);

        assertTrue(sessionDTO.isPresent());
        assertThat(sessionDTO.get()).isEqualTo(session.toDTO());
    }
    @Test
    void getAllNotificationsByUserId_happyPath() {
        Notification notification = mock(Notification.class);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);
        when(notificationManipulator.getAllNotificationsByUserId(any())).thenReturn(notifications);
        when(notification.toDTO()).thenReturn(mock(NotificationDTO.class));
        List<NotificationDTO> notificationDTOs = userService.getAllNotificationByUserId(1L);
        assertThat(notificationDTOs).hasSize(1);
    }
    @Test
    void deleteNotificationById_happyPath() {
        userService.deleteNotificationById(1L);
        verify(notificationManipulator, times(1)).deleteNotificationById(1L);
    }
    @Test
    void deleteAllNotificationsByUserId_happyPath() {
        userService.deleteAllNotificationsByUserId(1L);
        verify(notificationManipulator, times(1)).deleteAllNotificationsByUserId(1L);
    }
    @Test
    void hasNotificationByUserId_happyPath() {
        when(notificationManipulator.hasNotificationByUserId(any())).thenReturn(true);
        boolean hasNotification = userService.hasNotificationByUserId(1L);
        assertTrue(hasNotification);
    }
}

