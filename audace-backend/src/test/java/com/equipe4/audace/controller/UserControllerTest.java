package com.equipe4.audace.controller;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> userDTOs = List.of(
                new EmployerDTO(),
                new EmployerDTO()
        );
        when(userService.getAllUsers()).thenReturn(userDTOs);

        List<UserDTO> result = userController.getAllUsers();

        assertEquals(userDTOs, result);
    }

    @Test
    void testGetUser() {
        long userId = 1L;
        UserDTO userDTO = new EmployerDTO(userId, "peterson", "sara", "lesun@live.com", "password", "RocaFella Records", "artist", "3 York St", "4387253892", "slat");
        Optional<UserDTO> userOptional = Optional.of(userDTO);
        when(userService.getUser(userId)).thenReturn(userOptional);

        ResponseEntity<UserDTO> result = userController.getUser(userId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userDTO, result.getBody());
    }

    @Test
    void testGetUserNotFound() {
        long userId = 2L;
        Optional<UserDTO> userOptional = Optional.empty();
        when(userService.getUser(userId)).thenReturn(userOptional);

        ResponseEntity<UserDTO> result = userController.getUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
