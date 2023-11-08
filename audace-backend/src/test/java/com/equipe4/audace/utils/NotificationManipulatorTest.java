package com.equipe4.audace.utils;

import com.equipe4.audace.model.Student;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.notification.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        notificationManipulator.makeNotificationOfferToAllStudents(null, null);
    }
}
