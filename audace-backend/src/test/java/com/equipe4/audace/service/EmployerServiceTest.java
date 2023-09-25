package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.repository.EmployerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {
    @Mock
    public EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

    @Test
    public void createEmployer_HappyPath(){
        // Arrange
        EmployerDTO employerDTO = EmployerDTO.employerDTOBuilder().id(1L)
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1").build();
        when(employerRepository.save(any(Employer.class))).thenReturn(employerDTO.fromDTO());

        // Act
        EmployerDTO dto = employerService.createEmployer(employerDTO).get();

        // Assert
        assertThat(dto.equals(employerDTO));
        verify(employerRepository, times(1)).save(employerDTO.fromDTO());
    }
    @Test
    public void createEmployer_NullEmployer(){
        assertThatThrownBy(() -> employerService.createEmployer(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Employer cannot be null");
    }
    @Test
    void createEmployer_EmailAlreadyInUse() {
        // Arrange
        EmployerDTO employerDTO = EmployerDTO.employerDTOBuilder().id(1L)
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1").build();
        when(employerRepository.findByEmail(anyString())).thenReturn(Optional.of(employerDTO.fromDTO()));

        assertThatThrownBy(() -> employerService.createEmployer(employerDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already in use");
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