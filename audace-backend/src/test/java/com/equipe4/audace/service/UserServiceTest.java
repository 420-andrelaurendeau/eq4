package com.equipe4.audace.service;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Mock the userRepository behavior
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
        Student user = new Student(userId, "peterson", "sara", "lesun@live.com", "password", "100 rue Lapierre", "4387253891", "2080351", new Department());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserDTO> userDTO = userService.getUser(userId);

        assertTrue(userDTO.isPresent());
    }
}

