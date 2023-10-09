package com.equipe4.audace.service;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetUser() {
        long userId = 3L;
        Student user = new Student(userId, "peterson", "sara", "lesun@live.com", "password", "100 rue Lapierre", "4387253891", "2080351", new Department());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserDTO> userDTO = userService.getUser(userId);

        assertTrue(userDTO.isPresent());
    }
}

