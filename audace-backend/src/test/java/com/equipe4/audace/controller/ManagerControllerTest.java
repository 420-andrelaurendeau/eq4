package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.ApplicationRepository;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtManipulator jwtManipulator;
    @MockBean
    private NotificationRepository notificationRepository;
    @MockBean
    private CvRepository cvRepository;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ManagerRepository managerRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private SessionRepository sessionRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private ApplicationRepository applicationRepository;
    @MockBean
    private OfferSessionRepository offerSessionRepository;
    @MockBean
    private StudentService studentService;
    @MockBean
    private EmployerService employerService;
    @MockBean
    private ManagerService managerService;

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void acceptOffer() throws Exception {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                department,
                employer
        );
        OfferDTO offerDTO1 = offer1.toDTO();

        when(managerService.acceptOffer(1L, 1L)).thenReturn(Optional.of(offerDTO1));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/1/accept_offer/1").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void refuseOffer() throws Exception {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                department,
                employer
        );
        OfferDTO offerDTO1 = offer1.toDTO();
        when(managerService.refuseOffer(1L, 1L)).thenReturn(Optional.of(offerDTO1));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/1/refuse_offer/1").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void acceptOffer_invalidId() throws Exception {
        Employer employer = mock(Employer.class);
        Department department = mock(Department.class);
        Offer offer1 = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                department,
                employer
        );

        when(managerService.acceptOffer(-25L, -25L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/-25/accept_offer/-25").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toDTO().toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void refuseOffer_invalidId() throws Exception {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                department,
                employer
        );

        when(managerService.refuseOffer(-25L, -25L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/-25/refuse_offer/-25").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toDTO().toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getOffersByDepartment_happyPath() throws Exception {
        List<OfferDTO> offerDTOList = List.of(mock(OfferDTO.class));
        when(managerService.getOffersByDepartment(1L, 1L)).thenReturn(offerDTOList);

        mockMvc.perform(get("/managers/offers/{departmendId}/{sessionId}", 1L, 1L))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void acceptCv() throws Exception {
        Student student = mock(Student.class);
        CvDTO cvDTO = mock(CvDTO.class);
        Cv cv = new Cv(null, "One must imagine whoever puts the rock on top of the mountain happy", "cv".getBytes(), student);
        when(managerService.acceptCv(1L, 1L)).thenReturn(Optional.of(cvDTO));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/1/accept_cv/1").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(cv.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void refuseCv() throws Exception {
        Student student = mock(Student.class);
        CvDTO cvDTO = mock(CvDTO.class);
        Cv cv = new Cv(null, "One must imagine whoever puts the rock on top of the mountain happy", "cv".getBytes(), student);
        when(managerService.refuseCv(1L, 1L)).thenReturn(Optional.of(cvDTO));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/1/refuse_cv/1").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(cv.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void acceptCv_invalidId() throws Exception {
        Student student = new Student();
        Cv cv = new Cv(null, "One must imagine whoever puts the rock on top of the mountain happy", "cv".getBytes(), student);
        when(managerService.acceptCv(1L, 1L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/1/accept_offer/1L").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(cv.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void refuseCv_invalidId() throws Exception {
        Student student = new Student();
        Cv cv = new Cv(null, "One must imagine whoever puts the rock on top of the mountain happy", "cv".getBytes(), student);
        when(managerService.refuseCv(1L, 1L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/1/refuse_offer/1L").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(cv.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getManagerById_happyPath_test() throws Exception {
        Department department = new Department(1L, "yeete", "yaint");
        Manager manager = new Manager(
                1L,
                "manager",
                "managerman",
                "asd",
                "ads",
                "das",
                "sda",
                department
        );

        when(managerService.getManagerById(1L)).thenReturn(Optional.of(manager.toDTO()));

        mockMvc.perform(get("/managers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value(manager.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(manager.getLastName()))
                .andExpect(jsonPath("$.email").value(manager.getEmail()))
                .andExpect(jsonPath("$.password").value(manager.getPassword()))
                .andExpect(jsonPath("$.phone").value(manager.getPhone()))
                .andExpect(jsonPath("$.department.id").value(manager.getDepartment().getId()))
                .andExpect(jsonPath("$.department.name").value(manager.getDepartment().getName()));
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getManagerById_invalidId_test() throws Exception {
        when(managerService.getManagerById(-1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/managers/{id}", -1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getCvsByDepartment() throws Exception {
        List<CvDTO> cvDTOList = List.of(mock(CvDTO.class));
        when(managerService.getCvsByDepartment(1L, 1L)).thenReturn(cvDTOList);

        mockMvc.perform(get("/managers/cvs/1/1"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getAcceptedApplicationsByDepartment() throws Exception {
        List<ApplicationDTO> applicationDTOList = new ArrayList<>();
        Employer employer = new Employer(1L, "firstName", "lastName", "email", "password", "address", "phone", "companyName", "companyAddress", "companyPhone");
        Department department = new Department(1L, "yeete", "yaint");
        Student student = new Student(1L, "firstName", "lastName", "email", "password", "address", "phone", "studentNumber", department);
        Cv cv = new Cv(1L, "cv.pdf", "cv".getBytes(), student );
        Offer offer = new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, department, employer);
        Application application = new Application(1L, cv, offer);
        applicationDTOList.add(application.toDTO());
        when(managerService.getAcceptedApplicationsByDepartment(1L, 1L)).thenReturn(applicationDTOList);

        mockMvc.perform(get("/managers/1/acceptedApplications/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].offer.id").value(offer.getId()))
                .andExpect(jsonPath("$[0].offer.title").value(offer.getTitle()))
                .andExpect(jsonPath("$[0].offer.description").value(offer.getDescription()))
                .andExpect(jsonPath("$[0].offer.internshipStartDate").value(offer.getInternshipStartDate().toString()))
                .andExpect(jsonPath("$[0].offer.internshipEndDate").value(offer.getInternshipEndDate().toString()))
                .andExpect(jsonPath("$[0].offer.offerEndDate").value(offer.getOfferEndDate().toString()))
                .andExpect(jsonPath("$[0].offer.availablePlaces").value(offer.getAvailablePlaces()))
                .andExpect(jsonPath("$[0].offer.department.id").value(offer.getDepartment().getId()))
                .andExpect(jsonPath("$[0].offer.department.name").value(offer.getDepartment().getName()))
                .andExpect(jsonPath("$[0].applicationStatus").value(application.getApplicationStatus().toString()));
    }
    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getDepartmentByManager() throws Exception {
        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "Department 1", "Department 1");

        when(managerService.getDepartmentByManager(1L)).thenReturn(departmentDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/managers/1/department")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Department 1"));
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"Manager"})
    public void givenContractObject_whenCreateContract_thenReturnIsCreated() throws Exception {
        // given - precondition or setup
        ContractDTO contractDTO = createContractDTO();

        when(managerService.createContract(any(ContractDTO.class))).thenReturn(Optional.of(contractDTO));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/managers/contracts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Optional.of(contractDTO))));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(username = "manager", authorities = {"Manager"})
    public void givenContractId_whenGetContractById_thenReturnContractObject() throws Exception{
        // given - precondition or setup
        long contractId = 1L;
        ContractDTO contractDTO = createContractDTO();

        given(managerService.findContractById(contractId)).willReturn(Optional.of(contractDTO));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/managers/contracts/{contractId}", contractId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(contractDTO.getId()))
                .andExpect(jsonPath("$.officeName", is(contractDTO.getOfficeName())))
                .andExpect(jsonPath("$.startHour", is(contractDTO.getStartHour())))
                .andExpect(jsonPath("$.endHour", is(contractDTO.getEndHour())))
                .andExpect(jsonPath("$.totalHoursPerWeek", is(contractDTO.getTotalHoursPerWeek())))
                .andExpect(jsonPath("$.salary", is(contractDTO.getSalary())))
                .andExpect(jsonPath("$.internTasksAndResponsibilities", is(contractDTO.getInternTasksAndResponsibilities())))
                .andExpect(jsonPath("$.supervisor.email", is(contractDTO.getSupervisor().getEmail())))
                .andExpect(jsonPath("$.application.id", is(contractDTO.getApplication().getId().intValue())));
    }

    private Department createDepartment(){
        return new Department(1L, "GLO", "Génie logiciel");
    }
    private EmployerDTO createEmployerDTO() {
        return new EmployerDTO(1L, "Employer1", "Employer1", "employer1@gmail.com", "123456eE", "Organisation1", "Position1", "Class Service, Javatown, Qc H8N1C1", "123-456-7890", "12345");
    }
    private StudentDTO createStudentDTO() {
        DepartmentDTO departmentDTO = createDepartment().toDTO();
        return new StudentDTO(1L, "student", "studentman", "student@email.com", "password", "123 Street Street", "1234567890", "123456789", departmentDTO);
    }
    private Offer createOffer() {
        Employer employer = createEmployerDTO().fromDTO();
        Department department = createDepartment();
        return new Offer(1L,"Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
    }
    private Application createApplication(Offer offer) {
        Student student = createStudentDTO().fromDTO();
        Cv cv = mock(Cv.class);

        return new Application(1L, cv, offer);
    }

    private ContractDTO createContractDTO(){
        EmployerDTO employerDTO = createEmployerDTO();
        OfferDTO offerDTO = createOffer().toDTO();
        ApplicationDTO applicationDTO = createApplication(offerDTO.fromDTO()).toDTO();
        return new ContractDTO(1L, "Construction", "08:00", "17:00", 40, 18.35, "TODO", employerDTO, applicationDTO);
    }
}
