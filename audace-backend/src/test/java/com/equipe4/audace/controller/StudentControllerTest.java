package com.equipe4.audace.controller;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private EmployerService employerService;
    @MockBean
    private ManagerRepository managerRepository;

    @Test
    public void getOffersByDepartment_happyPath() throws Exception {
        List<OfferDTO> offerDTOList = List.of(mock(OfferDTO.class));
        when(studentService.getAcceptedOffersByDepartment(1L)).thenReturn(offerDTOList);

        mvc.perform(get("/students/offers/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentById_happyPath() throws Exception {
        Department department = new Department("dep", "artment");
        Student student = new Student(
                1L,
                "student",
                "studentman",
                "email@email.com",
                "password",
                "address",
                "phone",
                "matricule",
                department
        );

        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student.toDTO()));

        mvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("student"))
                .andExpect(jsonPath("$.lastName").value("studentman"))
                .andExpect(jsonPath("$.email").value(student.getEmail()))
                .andExpect(jsonPath("$.password").value(student.getPassword()))
                .andExpect(jsonPath("$.address").value(student.getAddress()))
                .andExpect(jsonPath("$.phone").value(student.getPhone()))
                .andExpect(jsonPath("$.studentNumber").value(student.getStudentNumber()))
                .andExpect(jsonPath("$.department.id").value(department.getId()))
                .andExpect(jsonPath("$.department.name").value(department.getName()));
    }

    @Test
    public void getStudentById_InvalidId() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        mvc.perform(get("/students/1"))
                .andExpect(status().isNotFound());
    }
}
