package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
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
    private JwtManipulator jwtManipulator;
    @MockBean
    private StudentService studentService;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private UserRepository userRepository;

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

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void getEmployerById_happyPath_test() throws Exception {
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
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void getEmployerById_notFound_test() throws Exception {
        when(employerService.findEmployerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/employers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenOfferObject_whenCreateOffer_thenReturnSavedOffer() throws Exception{
        // given - precondition or setup
        Employer employer = createEmployerDTO().fromDTO();

        OfferDTO offerDTO = createOffer(employer).toDTO();

        when(employerService.createOffer(any(OfferDTO.class))).thenReturn(Optional.of(offerDTO));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/employers/{id}/offers", 1L)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Optional.of(offerDTO))));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(offerDTO.getId()))
                .andExpect(jsonPath("$.title", is(offerDTO.getTitle())))
                .andExpect(jsonPath("$.description", is(offerDTO.getDescription())))
                .andExpect(jsonPath("$.internshipStartDate", is(offerDTO.getInternshipStartDate().toString())))
                .andExpect(jsonPath("$.internshipEndDate", is(offerDTO.getInternshipEndDate().toString())))
                .andExpect(jsonPath("$.offerEndDate", is(offerDTO.getOfferEndDate().toString())))
                .andExpect(jsonPath("$.availablePlaces", is(offerDTO.getAvailablePlaces())));
    }
    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenListOfOffers_whenGetAllOffers_thenReturnOffersList() throws Exception{
        // given - precondition or setup

        Employer employer = createEmployerDTO().fromDTO();

        List<OfferDTO> listOfOffers = new ArrayList<>();
        listOfOffers.add(createOffer(employer).toDTO());
        listOfOffers.add(createOffer(employer).toDTO());
        given(employerService.findAllOffersByEmployerId(employer.getId())).willReturn(listOfOffers);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/employers/{id}/offers", 1L));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfOffers.size())));
    }

    @Test
    @WithMockUser(username = "employer", authorities = {"EMPLOYER"})
    public void givenUpdatedOffer_whenUpdateOffer_thenReturnUpdateOfferObject() throws Exception{
        // given - precondition or setup
        Employer employer = createEmployerDTO().fromDTO();

        Offer offerSaved = createOffer(employer);
        OfferDTO offerDTOSaved = offerSaved.toDTO();

        Offer offerUpdated = createOffer(employer);
        offerUpdated.setAvailablePlaces(1);
        OfferDTO offerDTOUpdated = offerUpdated.toDTO();

        given(offerRepository.findById(offerDTOSaved.getId())).willReturn(Optional.of(offerSaved));
        given(employerService.updateOffer(any(OfferDTO.class))).willReturn(Optional.of(offerDTOUpdated));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/employers/{id}/offers", 1L)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offerDTOUpdated)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(offerDTOUpdated.getId().intValue())))
                .andExpect(jsonPath("$.title", is(offerDTOUpdated.getTitle())))
                .andExpect(jsonPath("$.description", is(offerDTOUpdated.getDescription())))
                .andExpect(jsonPath("$.internshipStartDate", is(offerDTOUpdated.getInternshipStartDate().toString())))
                .andExpect(jsonPath("$.internshipEndDate", is(offerDTOUpdated.getInternshipEndDate().toString())))
                .andExpect(jsonPath("$.offerEndDate", is(offerDTOUpdated.getOfferEndDate().toString())))
                .andExpect(jsonPath("$.availablePlaces", is(offerDTOUpdated.getAvailablePlaces())))
                .andExpect(jsonPath("$.employer.id", is(offerDTOUpdated.getEmployer().getId().intValue())))
                .andExpect(jsonPath("$.department.code", is(offerDTOUpdated.getDepartment().getCode())));
    }

    private EmployerDTO createEmployerDTO() {
        return new EmployerDTO(1L, "Employer1", "Employer1", "employer1@gmail.com", "123456eE", "Organisation1", "Position1", "Class Service, Javatown, Qc H8N1C1", "123-456-7890", "12345");
    }
    private Offer createOffer(Employer employer) {
        Department department = new Department(1L, "GLO", "Génie logiciel");
        return new Offer(1L,"Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, employer, department);
    }
}
