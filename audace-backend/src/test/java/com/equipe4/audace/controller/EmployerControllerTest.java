package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.utils.JwtManipulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployerService employerService;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private ManagerRepository managerRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtManipulator jwtManipulator;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getEmployerById_happyPath_test_asAdmin() throws Exception {
        getEmployerById_happyPath_test();
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void getEmployerById_happyPath_test_asEmployer() throws Exception {
        getEmployerById_happyPath_test();
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getEmployerById_happyPath_test_asManager() throws Exception {
        getEmployerById_happyPath_test();
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void getEmployerById_happyPath_test_asStudent() throws Exception {
        getEmployerById_happyPath_test();
    }

    private void getEmployerById_happyPath_test() throws Exception {
        Employer employer = Employer.employerBuilder()
                .id(1L)
                .firstName("Employer1")
                .lastName("Employer1")
                .email("")
                .build();

        when(employerService.findEmployerById(1L)).thenReturn(Optional.of(new EmployerDTO(employer)));

        mockMvc.perform(get("/employers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Employer1"))
                .andExpect(jsonPath("$.lastName").value("Employer1"))
                .andExpect(jsonPath("$.email").value(""));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getEmployerById_notFound_test_asAdmin() throws Exception {
        getEmployerById_notFound_test();
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void getEmployerById_notFound_test_asEmployer() throws Exception {
        getEmployerById_notFound_test();
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getEmployerById_notFound_test_asManager() throws Exception {
        getEmployerById_notFound_test();
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void getEmployerById_notFound_test_asStudent() throws Exception {
        getEmployerById_notFound_test();
    }

    private void getEmployerById_notFound_test() throws Exception{
        when(employerService.findEmployerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/employers/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}
