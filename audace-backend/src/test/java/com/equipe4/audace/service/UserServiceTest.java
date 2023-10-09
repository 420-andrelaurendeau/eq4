package com.equipe4.audace.service;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void testGetAllUsers() {
        Student student = Student.studentBuilder()
                .firstname("peterson").lastname("sara").email("lesun@live.com").password("password").address("100 rue Lapierre")
                .phone("4387253891").studentNumber("2080351").department(new Department())
                .build();
        student.setId(4L);

        Employer employer = Employer.employerBuilder()
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1")
                .build();
        List<User> users = List.of(student, employer);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> userDTOs = userService.getAllUsers();
        assertEquals(users.size(), userDTOs.size());
    }

    @Test
    void testGetUser() {
        long userId = 3L;
        Student user = Student.studentBuilder()
                .firstname("peterson").lastname("sara").email("lesun@live.com").password("password").address("100 rue Lapierre")
                .phone("4387253891").studentNumber("2080351").department(new Department())
                .build();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserDTO> userDTO = userService.getUser(userId);

        assertTrue(userDTO.isPresent());
    }
}

