package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.Supervisor;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Signature;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.notification.NotificationRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.ManagerService;
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
    private NotificationRepository notificationRepository;
    @MockBean
    private JwtManipulator jwtManipulator;
    @MockBean
    private CvRepository cvRepository;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private SessionRepository sessionRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private ManagerRepository managerRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private ApplicationRepository applicationRepository;
    @MockBean
    private OfferSessionRepository offerSessionRepository;
    @MockBean
    private ContractRepository contractRepository;
    @MockBean
    private StudentService studentService;
    @MockBean
    private EmployerService employerService;
    @MockBean
    private ManagerService managerService;

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
        when(studentService.getAcceptedOffersByDepartment(1L, 1L)).thenReturn(offerDTOList);

        mockMvc.perform(get("/students/offers/1/1"))
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

        Student student = new Student(1L, "student", "studentman", "student@email.com", "password", "123 Street Street", "1234567890", "123456789", department);

        Cv cv = mock(Cv.class);

        Offer offer = new Offer(1L, "Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
        Application application = new Application(1L, cv, offer);
        ApplicationDTO applicationDTO = application.toDTO();

        when(studentService.createApplication(any(ApplicationDTO.class))).thenReturn(Optional.of(applicationDTO));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/students/applications")
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
    void getApplicationsByStudentIdAndSessionId() throws Exception {
        List<ApplicationDTO> applicationDTOS = List.of(mock(ApplicationDTO.class));
        when(studentService.getApplicationsByStudentIdAndSessionId(1L, 1L)).thenReturn(applicationDTOS);

        mockMvc.perform(get("/students/appliedOffers/{sessionId}", 1L).param("studentId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void givenContractId_whenSignContract_thenReturnIsOk() throws Exception{
        // given - precondition or setup
        ApplicationDTO applicationDTO = createApplicationDTO(createOfferDTO(1L));
        applicationDTO.setApplicationStatus(Application.ApplicationStatus.ACCEPTED);

        ContractDTO contractDTO = createContractDTO(applicationDTO);
        Student student = createStudentDTO(createDepartmentDTO()).fromDTO();

        contractDTO.setStudentSignature(new Signature<Student>(student, LocalDate.now()));

        when(studentService.signContractForStudent(student.getId(), contractDTO.getId())).thenReturn(Optional.of(contractDTO));

        mockMvc.perform(put("/students/contract_signature")
                .param("studentId", "1")
                .param("contractId", "1")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void getContractByApplicationId_ContractExists() throws Exception {
        ApplicationDTO applicationDTO = createApplicationDTO(createOfferDTO(1L));
        ContractDTO mockContractDTO = createContractDTO(applicationDTO);

        when(studentService.getContractByApplicationId(applicationDTO.getId()))
                .thenReturn(Optional.of(mockContractDTO));

        ResultActions result = mockMvc.perform(
                get("/students/applications/{applicationId}/contract", applicationDTO.getId())
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockContractDTO.getId()));
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void signContractForStudent_ContractNotFound() throws Exception {
        Long studentId = 1L;
        Long contractId = 1L;

        when(studentService.signContractForStudent(studentId, contractId))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/students/contract_signature")
                        .param("studentId", studentId.toString())
                        .param("contractId", contractId.toString())
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void signContractForStudent_ContractExists() throws Exception {
        Long studentId = 1L;
        Long contractId = 1L;
        ContractDTO mockContractDTO = createContractDTO(createApplicationDTO(createOfferDTO(1L)));

        when(studentService.signContractForStudent(studentId, contractId))
                .thenReturn(Optional.of(mockContractDTO));

        mockMvc.perform(put("/students/contract_signature")
                        .param("studentId", studentId.toString())
                        .param("contractId", contractId.toString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockContractDTO.getId()));
    }

    private DepartmentDTO createDepartmentDTO(){
        return new DepartmentDTO(1L, "GLO", "Génie logiciel");
    }
    private ManagerDTO createManagerDTO(DepartmentDTO departmentDTO){
        return new ManagerDTO(1L, "manager", "managerman", "asd", "ads", "das", "sda", departmentDTO);
    }
    private EmployerDTO createEmployerDTO() {
        return new EmployerDTO(1L, "Employer1", "Employer1", "employer1@gmail.com", "123456eE", "Organisation1", "Position1", "Class Service, Javatown, Qc H8N1C1", "123-456-7890", "12345");
    }
    private StudentDTO createStudentDTO(DepartmentDTO departmentDTO) {
        return new StudentDTO(1L, "student", "studentman", "student@email.com", "password", "123 Street Street", "1234567890", "123456789", departmentDTO);
    }
    private CvDTO createCvDTO(StudentDTO studentDTO) {
        return new CvDTO(1L,"fileName", "content".getBytes(), Cv.CvStatus.PENDING, studentDTO);
    }
    private OfferDTO createOfferDTO(Long id) {
        EmployerDTO employerDTO = createEmployerDTO();
        DepartmentDTO departmentDTO = createDepartmentDTO();
        return new OfferDTO(id,"Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, Offer.OfferStatus.PENDING, departmentDTO, employerDTO);
    }
    private ApplicationDTO createApplicationDTO(OfferDTO offerDTO) {
        CvDTO cvDTO = createCvDTO(createStudentDTO(createDepartmentDTO()));
        return new ApplicationDTO(1L, cvDTO, offerDTO, Application.ApplicationStatus.PENDING);
    }

    private ContractDTO createContractDTO(ApplicationDTO applicationDTO){
        return new ContractDTO(1L, "08:00", "17:00", 40, 18.35, createSupervisor(), applicationDTO);
    }

    private Supervisor createSupervisor(){
        return new Supervisor("super", "visor", "supervisor@email.com", "supervisor", "1234567890", "-123");
    }
}
