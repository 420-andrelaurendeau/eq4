package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.contract.SignatureDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Supervisor;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.*;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.notification.NotificationRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.ManagerService;
import com.equipe4.audace.service.StudentService;
import com.equipe4.audace.utils.JwtManipulator;
import com.equipe4.audace.utils.SessionManipulator;
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
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtManipulator jwtManipulator;
    @MockBean
    private SessionManipulator sessionManipulator;
    @MockBean
    private NotificationRepository notificationRepository;
    @MockBean
    private CvRepository cvRepository;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private SessionRepository sessionRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private ManagerRepository managerRepository;
    @MockBean
    private EmployerRepository employerRepository;
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
    @WithMockUser(username = "employer", authorities = {"EMPLOYER", "USER"})
    public void getEmployerById_happyPath_test_asEmployer() throws Exception {
        getEmployerById_happyPath_test();
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER", "USER"})
    public void getEmployerById_happyPath_test_asManager() throws Exception {
        getEmployerById_happyPath_test();
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT", "USER"})
    public void getEmployerById_happyPath_test_asStudent() throws Exception {
        getEmployerById_happyPath_test();
    }

    private void getEmployerById_happyPath_test() throws Exception {
        EmployerDTO employerDTO = createEmployerDTO();

        when(employerService.findEmployerById(1L)).thenReturn(Optional.of(employerDTO));

        mockMvc.perform(get("/employers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Employer1"))
                .andExpect(jsonPath("$.lastName").value("Employer1"))
                .andExpect(jsonPath("$.email").value("employer1@gmail.com"));
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

        mockMvc.perform(get("/employers/{employerId}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenOfferObject_whenCreateOffer_thenReturnSavedOffer() throws Exception{
        // given - precondition or setup
        OfferDTO offerDTO = createOfferDTO(1L);

        when(employerService.createOffer(any(OfferDTO.class))).thenReturn(Optional.of(offerDTO));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/employers/offers")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Optional.of(offerDTO))));

        // then - verify the result or output using assert statements
        response.andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenListOfOffers_whenGetOffersByEmployerId_thenReturnOffersList() throws Exception{
        // given - precondition or setup
        Session session = createSession();
        EmployerDTO employerDTO = createEmployerDTO();
        OfferDTO offerDTO1 = createOfferDTO(1L);
        OfferDTO offerDTO2 = createOfferDTO(2L);

        List<OfferDTO> listOfOffers = new ArrayList<>();
        listOfOffers.add(offerDTO1);
        listOfOffers.add(offerDTO2);
        given(employerService.findAllOffersByEmployerIdAndSessionId(employerDTO.getId(), session.getId())).willReturn(listOfOffers);

        // when -  action or the behaviour that we are going test
        mockMvc.perform(get("/employers/offers/{sessionId}", 1L)
                .param("employerId", "1"))
        // then - verify the output
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfOffers.size())));
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenUpdatedOffer_whenUpdateOffer_thenReturnUpdateOfferObject() throws Exception{
        // given - precondition or setup
        OfferDTO offerSaved = createOfferDTO(1L);
        OfferDTO offerUpdated = createOfferDTO(2L);

        given(offerRepository.findById(offerSaved.getId())).willReturn(Optional.of(offerSaved.fromDTO()));
        given(employerService.updateOffer(any(OfferDTO.class))).willReturn(Optional.of(offerUpdated));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/employers/offers")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offerUpdated)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(offerUpdated.getId().intValue())))
                .andExpect(jsonPath("$.title", is(offerUpdated.getTitle())))
                .andExpect(jsonPath("$.description", is(offerUpdated.getDescription())))
                .andExpect(jsonPath("$.internshipStartDate", is(offerUpdated.getInternshipStartDate().toString())))
                .andExpect(jsonPath("$.internshipEndDate", is(offerUpdated.getInternshipEndDate().toString())))
                .andExpect(jsonPath("$.offerEndDate", is(offerUpdated.getOfferEndDate().toString())))
                .andExpect(jsonPath("$.availablePlaces", is(offerUpdated.getAvailablePlaces())))
                .andExpect(jsonPath("$.employer.id", is(offerUpdated.getEmployer().getId().intValue())))
                .andExpect(jsonPath("$.department.code", is(offerUpdated.getDepartment().getCode())));
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenEOfferId_whenDeleteOffer_thenReturnIsOd() throws Exception{
        // given - precondition or setup
        willDoNothing().given(employerService).deleteOffer(anyLong());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/employers/offers")
                .with(csrf())
                .param("offerId", "1")
                .accept(MediaType.APPLICATION_JSON)
        );

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenListOfApplications_whenGetAllApplicationsByEmployerIdAndOfferId_thenReturnApplicationsList() throws Exception{
        // given - precondition or setup
        EmployerDTO employerDTO = createEmployerDTO();
        OfferDTO offerDTO = createOfferDTO(1L);

        List<ApplicationDTO> applicationDTOList = new ArrayList<>();
        applicationDTOList.add(createApplicationDTO(offerDTO));
        applicationDTOList.add(createApplicationDTO(offerDTO));


        given(employerService.findAllApplicationsByEmployerIdAndOfferId(employerDTO.getId(), offerDTO.getId())).willReturn(applicationDTOList);

        // when -  action or the behaviour that we are going test
        mockMvc.perform(get("/employers/applications/{offerId}", 1L)
                .param("employerId", "1"))
                // then - verify the output
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(applicationDTOList.size())));
    }


    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void acceptApplication_HappyPath() throws Exception {
        ApplicationDTO applicationDTO = createApplicationDTO(createOfferDTO(1L));

        when(employerService.acceptApplication(1L, 1L)).thenReturn(Optional.of(applicationDTO));

        mockMvc.perform(put("/employers/accept_application/{applicationId}", 1L)
                .param("employerId", "1")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void acceptApplication_invalidId() throws Exception {
        when(employerService.acceptApplication(anyLong(), anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/employers/accept_application/{applicationId}", 1L)
                        .param("employerId", "1")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void refuseApplication_HappyPath() throws Exception {
        ApplicationDTO applicationDTO = createApplicationDTO(createOfferDTO(1L));

        when(employerService.refuseApplication(anyLong(), anyLong())).thenReturn(Optional.of(applicationDTO));

        mockMvc.perform(put("/employers/refuse_application/{applicationId}", 1L)
                .param("employerId", "1")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(applicationDTO.toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void refuseApplication_invalidId() throws Exception {
        when(employerService.refuseApplication(anyLong(), anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/employers/refuse_application/{applicationId}", 1L)
                        .param("employerId", "1")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void signContract_ContractNotFound() throws Exception {
        Long contractId = 1L;

        when(employerService.signContract(contractId))
                .thenThrow(new NoSuchElementException("Contract not found"));

        mockMvc.perform(post("/employers/sign_contract/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    void signContract_Success() throws Exception {
        ContractDTO contractDTO = createContractDTO(createApplicationDTO(createOfferDTO(1L)));
        SignatureDTO signatureDTO = createSignatureDTO();

        when(employerService.signContract(anyLong())).thenReturn(Optional.of(signatureDTO));

        mockMvc.perform(post("/employers/sign_contract/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenContractId_whenGetContractById_thenReturnContractObject() throws Exception{
        // given - precondition or setup
        ApplicationDTO applicationDTO = createApplicationDTO(createOfferDTO(1L));
        ContractDTO contractDTO = createContractDTO(applicationDTO);

        given(employerService.findContractById(anyLong())).willReturn(Optional.of(contractDTO));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/employers/contracts/{contractId}", 1L));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(contractDTO.getId()))
                .andExpect(jsonPath("$.startHour", is(contractDTO.getStartHour())))
                .andExpect(jsonPath("$.endHour", is(contractDTO.getEndHour())))
                .andExpect(jsonPath("$.totalHoursPerWeek", is(contractDTO.getTotalHoursPerWeek())))
                .andExpect(jsonPath("$.salary", is(contractDTO.getSalary())))
                .andExpect(jsonPath("$.supervisor.email", is(contractDTO.getSupervisor().getEmail())))
                .andExpect(jsonPath("$.application.id", is(contractDTO.getApplication().getId().intValue())));
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenContractId_whenGetSignatureByContractId_thenReturnSignatureList() throws Exception{
        // given - precondition or setup
        SignatureDTO signatureDTO = createSignatureDTO();
        List<SignatureDTO> signatureDTOList = List.of(signatureDTO);

        given(employerService.getSignaturesByContractId(anyLong())).willReturn(signatureDTOList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/employers/contracts/{contractId}/signatures", 1L));

        // then - verify the output
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    void getContractByApplicationId_ContractExists() throws Exception {
        ApplicationDTO applicationDTO = createApplicationDTO(createOfferDTO(1L));
        ContractDTO mockContractDTO = createContractDTO(applicationDTO);

        when(employerService.getContractByApplicationId(applicationDTO.getId()))
                .thenReturn(Optional.of(mockContractDTO));

        ResultActions result = mockMvc.perform(
                get("/employers/applications/{applicationId}/contract", applicationDTO.getId())
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockContractDTO.getId()));
    }

    private DepartmentDTO createDepartmentDTO(){
        return new DepartmentDTO(1L, "GLO", "Génie logiciel");
    }
    private EmployerDTO createEmployerDTO() {
        return new EmployerDTO(1L, "Employer1", "Employer1", "employer1@gmail.com", "123456eE", "Organisation1", "Position1", "Class Service, Javatown, Qc H8N1C1", "123-456-7890", "12345");
    }
    private StudentDTO createStudentDTO() {
        DepartmentDTO departmentDTO = createDepartmentDTO();
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
        CvDTO cvDTO = createCvDTO(createStudentDTO());
        return new ApplicationDTO(1L, cvDTO, offerDTO, Application.ApplicationStatus.PENDING);
    }
    private Session createSession(){
        return new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));
    }

    private ContractDTO createContractDTO(ApplicationDTO applicationDTO){
        return new ContractDTO(1L, "08:00", "17:00", 40, 18.35, createSupervisor(), applicationDTO);
    }

    private SignatureDTO createSignatureDTO() {
        return new SignatureDTO(1L, 1L, "signatureName", "signatureType", LocalDate.now());
    }

    private Supervisor createSupervisor(){
        return new Supervisor("super", "visor", "supervisor@email.com", "supervisor", "1234567890", "-123");
    }
}
