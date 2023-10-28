package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.utils.SessionManipulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {
    @Mock
    public EmployerRepository employerRepository;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private SaltRepository saltRepository;
    @Mock
    private SessionManipulator sessionManipulator;
    @Mock
    private OfferSessionRepository offerSessionRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private EmployerService employerService;

    @Test
    public void createEmployer_HappyPath(){
        // Arrange
        EmployerDTO employerDTO = createEmployerDTO();

        when(employerRepository.save(any(Employer.class))).thenReturn(employerDTO.fromDTO());
        when(saltRepository.save(any())).thenReturn(mock(Salt.class));

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
        EmployerDTO employerDTO = createEmployerDTO();
        when(employerRepository.findByEmail(anyString())).thenReturn(Optional.of(employerDTO.fromDTO()));

        assertThatThrownBy(() -> employerService.createEmployer(employerDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already in use");
    }

    @Test
    public void findAllEmployersTest(){
        // Arrange
        List<Employer> employers = new ArrayList<>();
        Employer employer1 = createEmployerDTO().fromDTO();
        Employer employer2 = createEmployerDTO().fromDTO();
        employer2.setId(2L);

        employers.add(employer1);
        employers.add(employer2);
        when(employerRepository.findAll()).thenReturn(employers);

        // Act
        List<EmployerDTO> employerDTOList = employerService.findAllEmployers();

        // Assert
        assertThat(employerDTOList.size()).isEqualTo(2);
        verify(employerRepository, times(1)).findAll();
    }

    @Test
    public void findEmployerById_happyPathTest() {
        // Arrange
        Employer employer = createEmployerDTO().fromDTO();

        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));

        // Act
        EmployerDTO employerDTO = employerService.findEmployerById(1L).orElseThrow();

        // Assert
        assertThat(employerDTO.getFirstName()).isEqualTo("Employer1");
        assertThat(employerDTO.getLastName()).isEqualTo("Employer1");
        assertThat(employerDTO.getEmail()).isEqualTo("employer1@gmail.com");
    }

    @Test
    public void findEmployerById_notFoundTest() {
        // Arrange
        when(employerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<EmployerDTO> employerDTO = employerService.findEmployerById(1L);

        // Assert
        assertThat(employerDTO.isEmpty()).isTrue();
    }

    @Test
    public void createOffer_HappyPath(){
        // Arrange
        Department mockedDepartment = new Department(1L, "GLO", "Génie logiciel");
        Employer fakeEmployer = new Employer(
                1L,
                "Employer1",
                "Employer1",
                "asd@email.com",
                "password",
                "Organisation1",
                "Position1",
                "123-456-7890",
                "12345",
                "Class Service, Javatown, Qc H8N1C1"
        );
        fakeEmployer.setId(1L);

        Offer offer = new Offer(
                1L,
                "Stage en génie logiciel",
                "Stage en génie logiciel",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                3,
                mockedDepartment,
                fakeEmployer
        );
        OfferDTO offerDTO = offer.toDTO();

        when(offerRepository.save(offerDTO.fromDTO())).thenReturn(offer);
        when(offerSessionRepository.save(any())).thenReturn(mock(OfferSession.class));

        OfferDTO dto = employerService.createOffer(offerDTO).get();

        assertThat(dto.equals(offerDTO));
        verify(offerRepository, times(1)).save(offerDTO.fromDTO());
    }

    @Test
    void getOffers_HappyPath() {
        Department mockedDepartment = new Department(1L, "GLO", "Génie logiciel");
        Employer fakeEmployer = new Employer(
                1L,
                "Employer1",
                "Employer1",
                "asd@email.com",
                "password",
                "Organisation1",
                "Position1",
                "123-456-7890",
                "12345",
                "Class Service, Javatown, Qc H8N1C1"
        );
        fakeEmployer.setId(1L);

        List<Offer> offers = new ArrayList<>();
        Offer offer1 = createOffer();
        Offer offer2 = createOffer();
        offer2.setId(2L);

        offers.add(offer1);
        offers.add(offer2);

        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));

        when(employerRepository.findById(anyLong())).thenReturn(Optional.of(fakeEmployer));
        when(offerRepository.findAllByEmployer(any(Employer.class))).thenReturn(offers);
        when(sessionManipulator.removeOffersNotInSession(offers, session.getId())).thenReturn(offers);

        List<OfferDTO> offerDTOList = employerService.findAllOffersByEmployerId(fakeEmployer.getId(), session.getId());

        assertThat(offerDTOList.size()).isEqualTo(2);
        verify(offerRepository, times(1)).findAllByEmployer(fakeEmployer);
    }

    @Test
    void getAllOffersByEmployerId_NotFound() {
        when(employerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.findAllOffersByEmployerId(anyLong(), anyLong()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Employer not found");
    }

    @Test
    void getAllOffersByEmployerId_noOffers() {
        Employer employer = createEmployerDTO().fromDTO();
        when(employerRepository.findById(anyLong())).thenReturn(Optional.of(employer));
        when(offerRepository.findAllByEmployer(employer)).thenReturn(new ArrayList<>());

        List<OfferDTO> result = employerService.findAllOffersByEmployerId(1L, 1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void deleteOffer_HappyPath(){
        Department mockedDepartment = mock(Department.class);
        Employer fakeEmployer = new Employer(
                1L,
                "Employer1",
                "Employer1",
                "asd@email.com",
                "password",
                "Organisation1",
                "Position1",
                "123-456-7890",
                "12345",
                "Class Service, Javatown, Qc H8N1C1"
        );

        Offer offer1 = new Offer(
                1L,
                "Stage en génie logiciel",
                "Stage en génie logiciel",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                3,
                mockedDepartment,
                fakeEmployer
        );
        when(offerRepository.findById(offer1.getId())).thenReturn(Optional.of(offer1));
        when(sessionManipulator.isOfferInCurrentSession(offer1)).thenReturn(true);

        employerService.deleteOffer(offer1.getId());

        verify(offerRepository).delete(offer1);
    }

    @Test
    public void deleteOffer_OfferDontExists() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.deleteOffer(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    public void updateOffer_HappyPath() {
        Department mockedDepartment = new Department(1L, "GLO", "Génie logiciel");
        Employer fakeEmployer = new Employer(
                1L,
                "Employer1",
                "Employer1",
                "asd@email.com",
                "password",
                "Organisation1",
                "Position1",
                "123-456-7890",
                "12345",
                "Class Service, Javatown, Qc H8N1C1"
        );

        Offer offer = new Offer(
                1L,
                "Stage en génie logiciel",
                "Stage en génie logiciel",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                3,
                mockedDepartment,
                fakeEmployer
        );

        when(offerRepository.save(any(Offer.class))).thenReturn(offer);
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));

        OfferDTO originalOffer = employerService.createOffer(offer.toDTO()).get();

        offer.setAvailablePlaces(2);

        OfferDTO updatedOffer = employerService.updateOffer(offer.toDTO()).get();

        verify(offerRepository).save(offer);
        verify(offerRepository).findById(offer.getId());
        assertThat(originalOffer.getAvailablePlaces()).isEqualTo(3);
        assertThat(updatedOffer.getAvailablePlaces()).isEqualTo(2);
    }

    @Test
    public void updateOffer_OfferDontExists() {
        Department mockedDepartment = new Department(1L, "GLO", "Génie logiciel");
        Employer fakeEmployer = new Employer(
                1L,
                "Employer1",
                "Employer1",
                "asd@email.com",
                "password",
                "Organisation1",
                "Position1",
                "123-456-7890",
                "12345",
                "Class Service, Javatown, Qc H8N1C1"
        );

        Offer offer = new Offer(
                1L,
                "Stage en génie logiciel",
                "Stage en génie logiciel",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                3,
                mockedDepartment,
                fakeEmployer
        );

        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.updateOffer(offer.toDTO()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Offer not found");
    }
    @Test
    public void findAllApplicationsByEmployerIdAndOfferId() {
        Application application = new Application(1L, mock(Cv.class), createOffer());
        List<Application> applications = new ArrayList<>();
        applications.add(application);

        when(offerRepository.findByEmployerIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(application.getOffer()));
        when(applicationRepository.findAllByOffer(any(Offer.class))).thenReturn(applications);

        List<ApplicationDTO> result = employerService.findAllApplicationsByEmployerIdAndOfferId(1L, 1L);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(application.toDTO());
    }
    @Test
    public void findAllApplicationsByEmployerIdAndOfferId_invalidId() {
        when(offerRepository.findByEmployerIdAndId(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.findAllApplicationsByEmployerIdAndOfferId(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Offer not found");
    }

    private Department createDepartment(){
        return new Department(1L, "GLO", "Génie logiciel");
    }

    private EmployerDTO createEmployerDTO() {
        return new EmployerDTO(1L, "Employer1", "Employer1", "employer1@gmail.com", "123456eE", "Organisation1", "Position1", "Class Service, Javatown, Qc H8N1C1", "123-456-7890", "12345");
    }

    private StudentDTO createStudentDTO() {
        DepartmentDTO departmentDTO = createDepartment().toDTO();
        return new StudentDTO(1L, "student", "studentman", "student@email.com", "password", "123 Street Street", "1234567890", "123456789", departmentDTO);
    }

    private Offer createOffer() {
        Employer employer = createEmployerDTO().fromDTO();
        Department department = createDepartment();
        return new Offer(1L,"Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
    }

    private Application createApplication() {
        Offer offer = createOffer();
        Cv cv = mock(Cv.class);

        return new Application(1L, cv, offer);
    }
}