package com.equipe4.audace.controller;

import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import com.equipe4.audace.utils.JwtManipulator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentService studentService;
    @MockBean
    private EmployerService employerService;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
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
    private CvRepository cvRepository;

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void uploadCv_happyPath() throws Exception {
        MockMultipartFile file = createMockFile();
        when(studentService.saveCv(file, 1L)).thenReturn(Optional.of(mock(CvDTO.class)));

        mockMvc.perform(
                multipart("/students/upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA).with(csrf())
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void uploadCv_noFile() throws Exception {
        mockMvc.perform(multipart("/students/upload/1")
                        .contentType(MediaType.MULTIPART_FORM_DATA).with(csrf())
        ).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void getOffersByDepartment_happyPath() throws Exception {
        List<OfferDTO> offerDTOList = List.of(mock(OfferDTO.class));
        when(studentService.getAcceptedOffersByDepartment(1L)).thenReturn(offerDTOList);

        mockMvc.perform(get("/students/offers/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void getStudentById_happyPath() throws Exception {
        Department department = new Department(1L, "dep", "artment");
        Student student = new Student(
                1L,
                "student",
                "studentman",
                "student@email.com",
                "password",
                "123 Street Street",
                "1234567890",
                "123456789",
                department
        );

        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student.toDTO()));

        mockMvc.perform(get("/students/1"))
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

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isNotFound());
    }

    private MockMultipartFile createMockFile() {
        return new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void givenApplicationObject_whenCreateApplication_thenReturnSavedApplication() throws Exception{
        // given - precondition or setup
        Department department = new Department(1L, "GLO", "Génie logiciel");
        Employer employer = new Employer(1L, "Employer1", "Employer1", "asd@email.com", "password", "Organisation1", "Position1", "123-456-7890", "12345", "Class Service, Javatown, Qc H8N1C1");

        Cv cv = mock(Cv.class);

        Offer offer = new Offer(1L, "Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
        Application application = new Application(1L, cv, offer);
        ApplicationDTO applicationDTO = application.toDTO();

        when(studentService.createApplication(any(ApplicationDTO.class))).thenReturn(Optional.of(applicationDTO));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/students/{id}/applications", 1L)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Optional.of(applicationDTO))));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void getCvsByStudent() throws Exception {
        List<CvDTO> cvDTOList = List.of(mock(CvDTO.class));
        when(studentService.getCvsByStudent(1L)).thenReturn(cvDTOList);

        mockMvc.perform(get("/students/cvs/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void getOffersAppliedByStudentId() throws Exception {
        List<ApplicationDTO> applicationDTOS = List.of(mock(ApplicationDTO.class));
        when(studentService.getOffersStudentApplied(1L)).thenReturn(applicationDTOS);

        mockMvc.perform(get("/students/1/appliedOffers"))
                .andExpect(status().isOk());
    }
}
