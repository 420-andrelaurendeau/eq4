package com.equipe4.audace.controller;

import com.equipe4.audace.dto.ApplicationDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private EmployerService employerService;
    @MockBean
    private ManagerRepository managerRepository;

    @MockBean
    private CvRepository cvRepository;

    @Test
    void uploadCv_happyPath() throws Exception {
        MockMultipartFile file = createMockFile();
        when(studentService.saveCv(file, 1L)).thenReturn(Optional.of(mock(CvDTO.class)));

        mockMvc.perform(
                multipart("/students/upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isCreated());
    }
    @Test
    public void getOffersByDepartment_happyPath() throws Exception {
        List<OfferDTO> offerDTOList = List.of(mock(OfferDTO.class));
        when(studentService.getAcceptedOffersByDepartment(1L)).thenReturn(offerDTOList);

        mockMvc.perform(get("/students/offers/1"))
                .andExpect(status().isOk());
    }
    @Test
    void uploadCv_noFile() throws Exception {
        mockMvc.perform(
                multipart("/students/upload/1")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isBadRequest());
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
    public void getStudentById_happyPath() throws Exception {
        Department department = new Department("dep", "artment");
        Student student = Student.studentBuilder()
                .firstname("student")
                .lastname("studentman")
                .email("email@email.com")
                .password("password")
                .address("address")
                .phone("phone")
                .studentNumber("matricule")
                .department(department)
                .build();
        student.setId(1L);

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
                .andExpect(jsonPath("$.departmentDTO.id").value(department.getId()))
                .andExpect(jsonPath("$.departmentDTO.name").value(department.getName()));
    }

    @Test
    public void getStudentById_InvalidId() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenApplicationObject_whenCreateApplication_thenReturnSavedApplication() throws Exception{
        // given - precondition or setup
        Department department = new Department("GLO", "Génie logiciel");
        Employer employer = Employer.employerBuilder()
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1")
                .build();
        employer.setId(1L);
        Student student = Student.studentBuilder()
                .firstname("student")
                .lastname("studentman")
                .email("email@email.com")
                .password("password")
                .address("address")
                .phone("phone")
                .studentNumber("matricule")
                .department(department)
                .build();
        student.setId(1L);

        Cv cv = new Cv();
        cv.setId(1L);

        Offer offer = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(employer).department(department)
                .build();
        offer.setId(1L);
        Application application = Application.applicationBuilder()
                .student(student)
                .cv(cv)
                .offer(offer)
                .build();
        ApplicationDTO applicationDTO = application.toDTO();

        when(studentService.createApplication(any(ApplicationDTO.class))).thenReturn(Optional.of(applicationDTO));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/students/{id}/applications", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Optional.of(applicationDTO))));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(applicationDTO.getId())))
                .andExpect(jsonPath("$.studentId", is(applicationDTO.getStudentId().intValue())))
                .andExpect(jsonPath("$.cvId", is(applicationDTO.getCvId().intValue())))
                .andExpect(jsonPath("$.offerId", is(applicationDTO.getOfferId().intValue())));
    }
}
