package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {
    @Mock
    public EmployerRepository employerRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private DepartmentRepository departmentRepository;

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
        DepartmentDTO fakeDepartmentDto = new DepartmentDTO(
                1L,
                "420-B0",
                "Technique Informatique"
        );

        EmployerDTO fakeEmployerDto = new EmployerDTO(
                2L,
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

        LocalDate startDate = LocalDate.now();
        OfferDTO fakeOfferDto = new OfferDTO(3L,
                "title",
                "description",
                new Date(startDate.plusMonths(1).toEpochDay()),
                new Date(startDate.plusMonths(2).toEpochDay()),
                new Date(startDate.toEpochDay()),
                fakeEmployerDto.getId(),
                fakeDepartmentDto
        );

        when(offerRepository.existsById(anyLong())).thenReturn(false);
        when(employerRepository.existsById(anyLong())).thenReturn(true);
        when(departmentRepository.existsById(anyLong())).thenReturn(true);
        when(employerRepository.findById(anyLong())).thenReturn(Optional.of(fakeEmployerDto.fromDTO()));
        when(offerRepository.save(any(Offer.class))).thenReturn(fakeOfferDto.fromDto(fakeEmployerDto.fromDTO()));

        // Act
        OfferDTO createdOffer = employerService.createOffer(fakeOfferDto).orElse(null);

        // Assert
        assertThat(createdOffer).isNotNull();
        assertThat(createdOffer.getId()).isEqualTo(fakeOfferDto.getId());

        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    void createOffer_nullOffer() {
        // Act
        Optional<OfferDTO> createdOffer = employerService.createOffer(null);

        // Assert
        assertThat(createdOffer).isEmpty();
        verify(offerRepository, never()).save(any(Offer.class));
    }

    @Test
    void createOffer_invalidOffer() {
        // Arrange
        DepartmentDTO fakeDepartmentDto = new DepartmentDTO(
                1L,
                "420-B0",
                "Technique Informatique"
        );

        EmployerDTO fakeEmployerDto = new EmployerDTO(
                2L,
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

        LocalDate startDate = LocalDate.now();
        OfferDTO fakeOfferDto = new OfferDTO(1L,
                "",
                "",
                new Date(startDate.toEpochDay()),
                new Date(startDate.plusMonths(1).toEpochDay()),
                new Date(startDate.plusMonths(2).toEpochDay()),
                fakeEmployerDto.getId(),
                fakeDepartmentDto
        );

        when(offerRepository.existsById(anyLong())).thenReturn(true);

        // Act
        Optional<OfferDTO> createdOffer = employerService.createOffer(fakeOfferDto);


        // Assert
        assertThat(createdOffer).isEmpty();
        verify(offerRepository, never()).save(any(Offer.class));
    }

    @Test
    void createOffer_employerNotFound() {
        // Arrange
        DepartmentDTO fakeDepartmentDto = new DepartmentDTO(
                1L,
                "420-B0",
                "Technique Informatique"
        );

        LocalDate startDate = LocalDate.now();
        OfferDTO fakeOfferDto = new OfferDTO(3L,
                "title",
                "description",
                new Date(startDate.toEpochDay()),
                new Date(startDate.plusMonths(1).toEpochDay()),
                new Date(startDate.plusMonths(2).toEpochDay()),
                2L,
                fakeDepartmentDto
        );

        when(offerRepository.existsById(anyLong())).thenReturn(false);
        when(employerRepository.existsById(anyLong())).thenReturn(false);

        // Act
        Optional<OfferDTO> createdOffer = employerService.createOffer(fakeOfferDto);

        // Assert
        assertThat(createdOffer).isEmpty();
        verify(offerRepository, never()).save(any(Offer.class));
    }

    @Test
    void createOffer_departmentNotFound() {
        // Arrange
        EmployerDTO fakeEmployerDto = new EmployerDTO(
                2L,
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

        when(offerRepository.existsById(anyLong())).thenReturn(false);

        LocalDate startDate = LocalDate.now();
        OfferDTO fakeOfferDto = new OfferDTO(3L,
                "title",
                "description",
                new Date(startDate.toEpochDay()),
                new Date(startDate.plusMonths(1).toEpochDay()),
                new Date(startDate.plusMonths(2).toEpochDay()),
                fakeEmployerDto.getId(),
                null
        );

        // Act
        Optional<OfferDTO> createdOffer = employerService.createOffer(fakeOfferDto);

        // Assert
        assertThat(createdOffer).isEmpty();
        verify(offerRepository, never()).save(any(Offer.class));
    }

    @Test
    void createOffer_invalidDates() {
        // Arrange
        DepartmentDTO fakeDepartmentDto = new DepartmentDTO(
                1L,
                "420-B0",
                "Technique Informatique"
        );

        EmployerDTO fakeEmployerDto = new EmployerDTO(
                2L,
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

        LocalDate startDate = LocalDate.now();
        OfferDTO fakeOfferDto = new OfferDTO(3L,
                "title",
                "description",
                new Date(startDate.plusMonths(1).toEpochDay()),
                new Date(startDate.toEpochDay()),
                new Date(startDate.plusMonths(2).toEpochDay()),
                fakeEmployerDto.getId(),
                fakeDepartmentDto
        );


        // Act
        Optional<OfferDTO> createdOffer = employerService.createOffer(fakeOfferDto);

        // Assert
        assertThat(createdOffer).isEmpty();
        verify(offerRepository, never()).save(any(Offer.class));
    }

}