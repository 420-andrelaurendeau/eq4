package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.OfferSessionRepository;
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
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private EmployerService employerService;

    @Test
    public void createEmployer_HappyPath(){
        // Arrange
        Employer employer = createEmployer();

        when(employerRepository.save(any(Employer.class))).thenReturn(employer);
        when(saltRepository.save(any())).thenReturn(mock(Salt.class));

        // Act
        EmployerDTO dto = employerService.createEmployer(employer.toDTO()).get();

        // Assert
        assertThat(dto.equals(employer.toDTO()));
        verify(employerRepository, times(1)).save(any(Employer.class));
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
        Employer employer = createEmployer();
        when(employerRepository.findByEmail(anyString())).thenReturn(Optional.of(employer));

        assertThatThrownBy(() -> employerService.createEmployer(employer.toDTO()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already in use");
    }

    @Test
    public void findAllEmployersTest(){
        // Arrange
        List<Employer> employers = new ArrayList<>();
        Employer employer1 = createEmployer();
        Employer employer2 = createEmployer();
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
        Employer employer = createEmployer();

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
        Offer offer = createOffer(1L, createEmployer());

        when(offerRepository.save(any(Offer.class))).thenReturn(offer);
        when(offerSessionRepository.save(any())).thenReturn(mock(OfferSession.class));

        OfferDTO dto = employerService.createOffer(offer.toDTO()).get();

        assertThat(dto.equals(offer.toDTO()));
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    public void createOffer_NullOffer(){
        assertThatThrownBy(() -> employerService.createOffer(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Offer cannot be null");
    }

    @Test
    void getAllOffersByEmployerIdAndSessionId_HappyPath() {
        Employer employer = createEmployer();
        List<Offer> offers = new ArrayList<>();
        Offer offer1 = createOffer(1L, createEmployer());
        Offer offer2 = createOffer(2L, createEmployer());

        offers.add(offer1);
        offers.add(offer2);

        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));

        when(employerRepository.findById(anyLong())).thenReturn(Optional.of(employer));
        when(offerRepository.findAllByEmployer(any(Employer.class))).thenReturn(offers);
        when(sessionManipulator.removeOffersNotInSession(offers, session.getId())).thenReturn(offers);

        List<OfferDTO> offerDTOList = employerService.findAllOffersByEmployerIdAndSessionId(employer.getId(), session.getId());

        assertThat(offerDTOList.size()).isEqualTo(2);
        verify(offerRepository, times(1)).findAllByEmployer(employer);
    }

    @Test
    void getAllOffersByEmployerIdAndSessionId_NotFound() {
        when(employerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.findAllOffersByEmployerIdAndSessionId(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Employer not found");
    }

    @Test
    void getAllOffersByEmployerIdAndSessionId_noOffers() {
        Employer employer = createEmployer();
        when(employerRepository.findById(anyLong())).thenReturn(Optional.of(employer));
        when(offerRepository.findAllByEmployer(employer)).thenReturn(new ArrayList<>());

        List<OfferDTO> result = employerService.findAllOffersByEmployerIdAndSessionId(1L, 1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void deleteOffer_HappyPath(){
        Offer offer = createOffer(1L, createEmployer());

        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));
        when(sessionManipulator.isOfferInCurrentSession(offer)).thenReturn(true);
        when(offerSessionRepository.findByOffer(offer)).thenReturn(Optional.of(new OfferSession()));

        employerService.deleteOffer(offer.getId());

        verify(offerRepository).delete(offer);
    }

    @Test
    public void deleteOffer_OfferDontExists() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.deleteOffer(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Offer not found");
    }

    @Test
    public void updateOffer_HappyPath() {
        Offer offer = createOffer(1L, createEmployer());

        when(offerRepository.save(any(Offer.class))).thenReturn(offer);
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));
        when(sessionManipulator.isOfferInCurrentSession(any(Offer.class))).thenReturn(true);

        OfferDTO originalOffer = employerService.createOffer(offer.toDTO()).get();

        offer.setAvailablePlaces(2);

        OfferDTO updatedOffer = employerService.updateOffer(offer.toDTO()).get();

        verify(offerRepository, times(2)).save(any(Offer.class));
        verify(offerRepository).findById(offer.getId());
        assertThat(originalOffer.getAvailablePlaces()).isEqualTo(3);
        assertThat(updatedOffer.getAvailablePlaces()).isEqualTo(2);
    }

    @Test
    public void updateOffer_OfferDontExists() {
        Offer offer = createOffer(1L, createEmployer());

        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.updateOffer(offer.toDTO()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Offer not found");
    }
    @Test
    public void findAllApplicationsByEmployerIdAndOfferId() {
        Application application = new Application(1L, createCv(), createOffer(1L, createEmployer()));
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

    @Test
    public void acceptApplication_HappyPath() {
        Application application = new Application(1L, createCv(), createOffer(1L, createEmployer()));

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        ApplicationDTO applicationDTO = employerService.acceptApplication(1L, 1L).orElseThrow();
        assertThat(applicationDTO.getApplicationStatus()).isEqualTo(Application.ApplicationStatus.ACCEPTED);
    }

    @Test
    public void acceptApplication_invalidId() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.acceptApplication(1L, 1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("Application not found");
    }

    @Test
    public void acceptApplication_noMorePlaces() {
        Offer offer = createOffer(1L, createEmployer());
        offer.setAvailablePlaces(0);
        Application application = new Application(1L, createCv(), offer);

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));

        assertThatThrownBy(() -> employerService.acceptApplication(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No more places available");
    }
    @Test
    public void acceptApplication_notOwnedByEmployer() {
        Application application = new Application(1L, createCv(), createOffer(1L, createEmployer()));

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));

        assertThatThrownBy(() -> employerService.acceptApplication(2L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Employer does not own this application");
    }


    @Test
    public void refuseApplication_HappyPath() {
        Application application = new Application(1L, createCv(), createOffer(1L, createEmployer()));

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        ApplicationDTO applicationDTO = employerService.refuseApplication(1L, 1L).orElseThrow();
        assertThat(applicationDTO.getApplicationStatus()).isEqualTo(Application.ApplicationStatus.REFUSED);
    }
    @Test
    public void refuseApplication_invalidId() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.refuseApplication(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Application not found");
    }
    @Test
    public void refuseApplication_notOwnedByEmployer() {
        Application application = new Application(1L, createCv(), createOffer(1L, createEmployer()));

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));

        assertThatThrownBy(() -> employerService.acceptApplication(2L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Employer does not own this application");
    }


    private Department createDepartment(){
        return new Department(1L, "GLO", "Génie logiciel");
    }

    private Employer createEmployer() {
        return new Employer(1L, "Employer1", "Employer1", "employer1@gmail.com", "123456eE", "Organisation1", "Position1", "Class Service, Javatown, Qc H8N1C1", "123-456-7890", "12345");
    }
    private Student createStudent() {
        Department department = createDepartment();
        return new Student(1L, "student", "studentman", "student@email.com", "password", "123 Street Street", "1234567890", "123456789", department);
    }

    private Cv createCv() {
        return new Cv(1L,"fileName", "content".getBytes(),createStudent());
    }

    private Offer createOffer(Long id, Employer employer) {
        Department department = createDepartment();
        return new Offer(id,"Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
    }
}