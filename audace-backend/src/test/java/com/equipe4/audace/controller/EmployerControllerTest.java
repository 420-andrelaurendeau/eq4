package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
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
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import com.equipe4.audace.service.EmployerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.equipe4.audace.service.StudentService;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
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
    private EmployerService employerService;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private ManagerRepository managerRepository;
    @MockBean
    private CvRepository cvRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtManipulator jwtManipulator;
    @MockBean
    private StudentService studentService;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private SessionRepository sessionRepository;
    @MockBean
    private OfferSessionRepository offerSessionRepository;

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
        Employer employer = new Employer(
                1L,
                "Employer1",
                "Employer1",
                "employer1@gmail.com",
                "password",
                "org",
                "extension",
                "blorg",
                "norg",
                "canorg"
        );

        when(employerService.findEmployerById(1L)).thenReturn(Optional.of(employer.toDTO()));

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
        OfferDTO offerDTO = createOfferDTO();

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
        EmployerDTO employerDTO = createEmployerDTO();
        OfferDTO offerDTO1 = createOfferDTO();
        OfferDTO offerDTO2 = createOfferDTO();
        offerDTO2.setId(2L);

        List<OfferDTO> listOfOffers = new ArrayList<>();
        listOfOffers.add(offerDTO1);
        listOfOffers.add(offerDTO2);
        given(employerService.findAllOffersByEmployerId(employerDTO.getId())).willReturn(listOfOffers);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/employers/offers").param("employerId", "1"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfOffers.size())));
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenUpdatedOffer_whenUpdateOffer_thenReturnUpdateOfferObject() throws Exception{
        // given - precondition or setup
        OfferDTO offerSaved = createOfferDTO();
        OfferDTO offerUpdated = createOfferDTO();
        offerUpdated.setAvailablePlaces(1);

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
    public void givenMapOfOffersAndApplications_whenGetAllApplicationsByEmployerId_thenReturnOffersAndApplicationsMap() throws Exception{
        // given - precondition or setup
        EmployerDTO employerDTO = createEmployerDTO();
        OfferDTO offerDTO1 = createOfferDTO();
        OfferDTO offerDTO2 = createOfferDTO();
        offerDTO2.setId(2L);

        List<ApplicationDTO> applicationDTOList1 = new ArrayList<>();
        applicationDTOList1.add(createApplicationDTO(offerDTO1));
        applicationDTOList1.add(createApplicationDTO(offerDTO1));

        List<ApplicationDTO> applicationDTOList2 = new ArrayList<>();
        applicationDTOList2.add(createApplicationDTO(offerDTO2));
        applicationDTOList2.add(createApplicationDTO(offerDTO2));

        Map<Long, List<ApplicationDTO>> map = new HashMap<>();
        map.put(offerDTO1.getId(), applicationDTOList1);
        map.put(offerDTO2.getId(), applicationDTOList2);

        given(employerService.findAllApplicationsByEmployerId(employerDTO.getId())).willReturn(map);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/employers/offers/applications").param("employerId", "1"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(map.size())));
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void getAllApplicationsByEmployerIdandOfferId() throws Exception {
        Department department = new Department(1L, "GLO", "Génie logiciel");
        Employer employer = createEmployerDTO().fromDTO();
        Offer offer = createOffer(employer, department);
        Application application = createApplication(offer);
        List<ApplicationDTO> listOfApplications = new ArrayList<>();
        listOfApplications.add(application.toDTO());
        listOfApplications.add(application.toDTO());
        given(employerService.findAllApplicationsByEmployerIdAndOfferId(employer.getId(), offer.getId())).willReturn(listOfApplications);

        ResultActions response = mockMvc.perform(get("/employers/{id}/offers/{offerId}/applications", 1L, 1L));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfApplications.size())));
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void acceptApplication_HappyPath() throws Exception {
        ApplicationDTO applicationDTO = createApplicationDTO(createOfferDTO());

        when(employerService.acceptApplication(anyLong(), anyLong())).thenReturn(Optional.of(applicationDTO));

        mockMvc.perform(post("/employers/accept_application/{applicationId}", 1L)
                .param("employerId", "1")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(applicationDTO.toString())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void acceptApplication_invalidId() throws Exception {
        when(employerService.acceptApplication(anyLong(), anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/employers/accept_application/{applicationId}", 1L)
                        .param("employerId", "1")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void refuseApplication_HappyPath() throws Exception {
        ApplicationDTO applicationDTO = createApplicationDTO(createOfferDTO());

        when(employerService.refuseApplication(anyLong(), anyLong())).thenReturn(Optional.of(applicationDTO));

        mockMvc.perform(post("/employers/refuse_application/{applicationId}", 1L)
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

        mockMvc.perform(post("/employers/refuse_application/{applicationId}", 1L)
                        .param("employerId", "1")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
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

    private CvDTO createCvDTO() {
        return new CvDTO(1L,"fileName", "content".getBytes(), Cv.CvStatus.PENDING, createStudentDTO());
    }

    private OfferDTO createOfferDTO() {
        EmployerDTO employerDTO = createEmployerDTO();
        DepartmentDTO departmentDTO = createDepartmentDTO();
        return new OfferDTO(1L,"Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, Offer.OfferStatus.PENDING, departmentDTO, employerDTO);
    }

    private ApplicationDTO createApplicationDTO(OfferDTO offerDTO) {
        StudentDTO studentDTO = createStudentDTO();
        CvDTO cvDTO = createCvDTO();

        return new ApplicationDTO(1L, cvDTO, offerDTO, Application.ApplicationStatus.PENDING);
    }
}
