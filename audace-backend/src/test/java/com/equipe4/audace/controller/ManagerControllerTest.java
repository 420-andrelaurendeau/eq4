package com.equipe4.audace.controller;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
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
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.ManagerService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private StudentRepository studentRepository;
    @MockBean
    private ManagerRepository managerRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtManipulator jwtManipulator;
    @MockBean
    private StudentService studentService;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private EmployerService employerService;
    @MockBean
    private CvRepository cvRepository;

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
        when(managerService.acceptOffer(1L)).thenReturn(Optional.of(offerDTO1));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/accept_offer/1").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());
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
        when(managerService.refuseOffer(1L)).thenReturn(Optional.of(offerDTO1));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/refuse_offer/1").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());
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

        when(managerService.acceptOffer(-25L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/accept_offer/-25").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toDTO().toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isBadRequest());
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

        when(managerService.refuseOffer(-25L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/managers/refuse_offer/-25").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(offer1.toDTO().toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    public void getOffersByDepartment_happyPath() throws Exception {
        List<OfferDTO> offerDTOList = List.of(mock(OfferDTO.class));
        when(managerService.getOffersByDepartment(1L)).thenReturn(offerDTOList);

        mvc.perform(get("/managers/offers/1"))
                .andExpect(status().isOk());
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

        mvc.perform(get("/managers/{id}", 1L))
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

        mvc.perform(get("/managers/{id}", -1L))
                .andExpect(status().isNotFound());
    }
}
