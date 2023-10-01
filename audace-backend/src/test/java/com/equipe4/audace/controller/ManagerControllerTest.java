package com.equipe4.audace.controller;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ManagerService managerService;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployerService employerService;


    @Test
    public void acceptOffer() throws Exception {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(2)
                .employer(employer).department(department)
                .build();
        when(managerService.acceptOffer(1L)).thenReturn(Optional.of(new OfferDTO(offer1)));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/accept_offer/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());
    }
    @Test
    public void refuseOffer() throws Exception {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(2)
                .employer(employer).department(department)
                .build();
        when(managerService.refuseOffer(1L)).thenReturn(Optional.of(new OfferDTO(offer1)));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/refuse_offer/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());
    }
    @Test
    public void acceptOffer_invalidId() throws Exception {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(2)
                .employer(employer).department(department)
                .build();

        when(managerService.acceptOffer(-25L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/accept_offer/-25")
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isBadRequest());
    }
    @Test
    public void refuseOffer_invalidId() throws Exception {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(2)
                .employer(employer).department(department)
                .build();

        when(managerService.refuseOffer(-25L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/refuse_offer/-25")
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isBadRequest());
    }
}
