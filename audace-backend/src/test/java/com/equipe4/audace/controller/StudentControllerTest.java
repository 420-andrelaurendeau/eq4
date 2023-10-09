package com.equipe4.audace.controller;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import com.equipe4.audace.utils.JwtManipulator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private ManagerRepository managerRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtManipulator jwtManipulator;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private EmployerService employerService;

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void getOffersByDepartment_happyPath() throws Exception {
        List<OfferDTO> offerDTOList = List.of(mock(OfferDTO.class));
        when(studentService.getAcceptedOffersByDepartment(1L)).thenReturn(offerDTOList);

        mvc.perform(get("/students/offers/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
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
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void getStudentById_InvalidId() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        mvc.perform(get("/students/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void uploadCv_happyPath() throws Exception {
        MockMultipartFile file = createMockFile();
        when(studentService.saveCv(file, 1L)).thenReturn(Optional.of(mock(CvDTO.class)));

        mvc.perform(
                multipart("/students/upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA).with(csrf())
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void uploadCv_noFile() throws Exception {
        mvc.perform(
                multipart("/students/upload/1")
                        .contentType(MediaType.MULTIPART_FORM_DATA).with(csrf())
        )
        .andExpect(status().isBadRequest());
    }

    private MockMultipartFile createMockFile() {
        return new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }
}
