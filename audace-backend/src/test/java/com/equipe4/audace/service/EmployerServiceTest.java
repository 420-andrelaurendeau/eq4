package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {
    @Mock
    public EmployerRepository employerRepository;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private EmployerService employerService;

    @Test
    public void saveEmployerTest(){
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


    /** Employer offer tests */

    @Test
    void createOffer_happyPath() {
        // Arrange
        Department mockedDepartment = mock(Department.class);
        EmployerDTO fakeEmployerDto = new EmployerDTO(
                1L,
                "employer",
                "employerman",
                "email@gmail.com",
                "password",
                "organisation",
                "position",
                "address",
                "phone",
                "extension"
        );
        Employer fakeEmployer = fakeEmployerDto.fromDTO();

        LocalDate startDate = LocalDate.now();
        Offer fakeOffer = new Offer(
                "title",
                "description",
                new Date(startDate.toEpochDay()),
                new Date(startDate.plusMonths(1).toEpochDay()),
                new Date(startDate.plusMonths(2).toEpochDay()),
                fakeEmployer,
                mockedDepartment
        );

        when(employerRepository.save(any(Employer.class))).thenReturn(fakeEmployer);

        // Act
        EmployerDTO dto = employerService.createEmployer(fakeEmployerDto).orElse(null);
        OfferDTO createdOffer = employerService.createOffer(fakeOffer.toDto()).orElse(null);

        // Assert
        assertThat(createdOffer).isNotNull();
        assertThat(employerService.findEmployerById(createdOffer.getEmployerId())).isEqualTo(fakeEmployer);

        verify(employerRepository, times(1)).save(any(Employer.class));
    }

    @Test
    void createOffer_offerAlreadyExists() {

    }

    @Test
    void createOffer_nullOffer() {

    }

    @Test
    void createOffer_employerNotFound() {

    }

    @Test
    void createOffer_departmentNotFound() {

    }

    @Test
    void createOffer_invalidDates() {

    }

}