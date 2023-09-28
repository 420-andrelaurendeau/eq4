package com.equipe4.audace.controller;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.service.DepartmentService;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.OfferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    EmployerRepository employerRepository;

    @MockBean
    private OfferService offerService;

    @Test
    public void givenOfferObject_whenCreateOffer_thenReturnSavedOffer() throws Exception{
        // given - precondition or setup
        Department department = new Department("GLO", "Génie logiciel");
        Employer employer = Employer.employerBuilder()
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1")
                .build();
        employer.setId(1L);

        Offer offer = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(employer).department(department)
                .build();
        OfferDTO offerDTO = new OfferDTO(offer);

        when(offerService.createOffer(any(OfferDTO.class))).thenReturn(Optional.of(offerDTO));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Optional.of(offerDTO))));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(offerDTO.getId())))
                .andExpect(jsonPath("$.title", is(offerDTO.getTitle())))
                .andExpect(jsonPath("$.description", is(offerDTO.getDescription())))
                .andExpect(jsonPath("$.internshipStartDate", is(offerDTO.getInternshipStartDate().toString())))
                .andExpect(jsonPath("$.internshipEndDate", is(offerDTO.getInternshipEndDate().toString())))
                .andExpect(jsonPath("$.offerEndDate", is(offerDTO.getOfferEndDate().toString())))
                .andExpect(jsonPath("$.availablePlaces", is(offerDTO.getAvailablePlaces())))
                .andExpect(jsonPath("$.approved", is(offerDTO.isApproved())))
                .andExpect(jsonPath("$.employerId", is(offerDTO.getEmployerId().intValue())))
                .andExpect(jsonPath("$.departmentCode", is(offerDTO.getDepartmentCode())));
    }

    @Test
    public void givenListOfOffers_whenGetAllOffers_thenReturnOffersList() throws Exception{
        // given - precondition or setup
        Department department = new Department("GLO", "Génie logiciel");
        Employer employer = Employer.employerBuilder()
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1")
                .build();
        employer.setId(1L);

        List<OfferDTO> listOfOffers = new ArrayList<>();
        listOfOffers.add(OfferDTO.offerDTOBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employerId(employer.getId()).departmentCode(department.getCode())
                .build());
        listOfOffers.add(OfferDTO.offerDTOBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(5).employerId(employer.getId()).departmentCode(department.getCode())
                .build());
        given(offerService.findAllOffers()).willReturn(listOfOffers);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/offers"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfOffers.size())));

    }

    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
        // given - precondition or setup

        Department department = new Department("GLO", "Génie logiciel");
        Employer employer = Employer.employerBuilder()
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1")
                .build();
        employer.setId(1L);

        Offer offerSaved = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(employer).department(department)
                .build();
        offerSaved.setId(1L);
        OfferDTO offerDTOSaved = new OfferDTO(offerSaved);

        Offer offerUpdated = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(5).employer(employer).department(department)
                .build();
        offerUpdated.setId(1L);
        OfferDTO offerDTOUpdated = new OfferDTO(offerUpdated);


        given(offerService.findOfferById(offerDTOSaved.getId())).willReturn(offerSaved);
        given(offerService.updateOffer(any(OfferDTO.class))).willReturn(Optional.of(offerDTOUpdated));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/offers")
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
                .andExpect(jsonPath("$.approved", is(offerDTOUpdated.isApproved())))
                .andExpect(jsonPath("$.employerId", is(offerDTOUpdated.getEmployerId().intValue())))
                .andExpect(jsonPath("$.departmentCode", is(offerDTOUpdated.getDepartmentCode())));
    }


}
