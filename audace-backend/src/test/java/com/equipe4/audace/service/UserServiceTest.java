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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                new Student(4L, "peterson", "sara", "lesun@live.com", "password", "100 rue Lapierre", "4387253891", "2080351", new Department()),
                new Employer()
        );
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> userDTOs = userService.getAllUsers();
        assertEquals(users.size(), userDTOs.size());
    }

    @Test
    void testGetUser() {
        long userId = 3L;

        Department department = new Department(1L, "department", "department");
        User user = new Student(userId, "student", "studentman", "student@email.com", "password", "123 Street Street", "1234567890", "123456789", department);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        Optional<UserDTO> userDTO = userService.getUser(userId);

        assertTrue(userDTO.isPresent());
    }
}

