package com.equipe4.audace.Service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.service.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {
    @InjectMocks
    private EmployerService employerService;

    @Mock
    public EmployerRepository employerRepository;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveEmployerTest(){
        // Arrange
        EmployerDTO employerDTO1 = EmployerDTO.employerDTOBuilder()
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1").build();
        when(employerRepository.save(employerDTO1.fromDTO())).thenReturn(employerDTO1.fromDTO());

        // Act
        EmployerDTO dto = employerService.saveEmployer(employerDTO1).get();

        // Assert
        assertThat(dto.equals(employerDTO1));
        verify(employerRepository, times(1)).save(employerDTO1.fromDTO());
    }

    @Test
    public void findAllEmployersTest(){
        // Arrange
        List<Employer> employers = new ArrayList<>();
        Employer employer1 = Employer.employerBuilder()
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1").build();
        Employer employer2 = Employer.employerBuilder()
                .firstName("Employer2").lastName("Employer2").email("employer2@gmail.com").password("Abcdef78")
                .organisation("Organisation2").position("Position2").phone("234-567-8910").extension("23456")
                .address("Commit, Javatown, Qc, H8N1C2").build();

        employers.add(employer1);
        employers.add(employer2);
        when(employerRepository.findAll()).thenReturn(employers);

        // Act
        List<EmployerDTO> employerDTOList = employerService.findAllEmployers();

        // Assert
        assertThat(employerDTOList.size()).isEqualTo(2);
        verify(employerRepository, times(1)).findAll();
    }
}