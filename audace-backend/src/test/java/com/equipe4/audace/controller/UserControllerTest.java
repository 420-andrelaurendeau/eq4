package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private EmployerRepository employerRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDTO> userDTOs = List.of(
                new EmployerDTO(1L, "peterson", "sara", "lesun@live.com", "password", "RocaFella Records", "artist", "3 York St", "4387253892", "slat"),
                new EmployerDTO(2L, "addison", "sara", "lesun@live.com", "password", "RocaFella Records", "artist", "3 York St", "4387253892", "slat")
        );
        when(userService.getAllUsers()).thenReturn(Optional.of(userDTOs));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(userDTOs.size())));
    }
    @Test
    void testGetUser() throws Exception {
        long userId = 1L;
        UserDTO userDTO = new EmployerDTO(userId, "peterson", "sara", "lesun@live.com", "password", "RocaFella Records", "artist", "3 York St", "4387253892", "slat");
        Optional<UserDTO> userOptional = Optional.of(userDTO);
        when(userService.getUser(userId)).thenReturn(userOptional);

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDTO.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(userDTO.getEmail())))
                .andExpect(jsonPath("$.address", is(userDTO.getAddress())))
                .andExpect(jsonPath("$.phone", is(userDTO.getPhone())));
    }

    @Test
    void testGetUserNotFound() throws Exception {
        when(userService.getUser(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}
