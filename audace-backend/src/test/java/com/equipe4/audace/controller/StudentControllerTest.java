package com.equipe4.audace.controller;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private EmployerRepository employerRepository;

    @Test
    public void getOffersByDepartment_happyPath() throws Exception {
        List<OfferDTO> offerDTOList = List.of(mock(OfferDTO.class));
        when(studentService.getAcceptedOffersByDepartment(1L)).thenReturn(offerDTOList);

        mvc.perform(get("/students/offers/1"))
                .andExpect(status().isOk());
    }
}
