package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.contract.SignatureDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.Supervisor;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.contract.Signature;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.signature.SignatureRepository;
import com.equipe4.audace.utils.NotificationManipulator;
import com.equipe4.audace.utils.SessionManipulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @Mock
    private NotificationManipulator notificationManipulator;
    @InjectMocks
    private EmployerService employerService;
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private SignatureRepository signatureRepository;

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
        verify(notificationManipulator, times(1)).makeNotificationOfferToAllManagers(any(Offer.class), any(Notification.NotificationCause.class));
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
        when(sessionManipulator.removeOffersNotInNextSession(offers, session.getId())).thenReturn(offers);

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
        when(sessionManipulator.isOfferInNextSession(offer)).thenReturn(true);
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
        when(sessionManipulator.isOfferInNextSession(any(Offer.class))).thenReturn(true);

        OfferDTO originalOffer = employerService.createOffer(offer.toDTO()).get();

        offer.setAvailablePlaces(2);

        OfferDTO updatedOffer = employerService.updateOffer(offer.toDTO()).get();

        verify(offerRepository, times(2)).save(any(Offer.class));
        verify(offerRepository).findById(offer.getId());
        assertThat(originalOffer.getAvailablePlaces()).isEqualTo(3);
        assertThat(updatedOffer.getAvailablePlaces()).isEqualTo(2);
        verify(notificationManipulator, times(2)).makeNotificationOfferToAllManagers(any(Offer.class), any(Notification.NotificationCause.class));
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
        verify(notificationManipulator, times(1)).makeNotificationApplicationToStudent(any(Application.class), any(Notification.NotificationCause.class));
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
        verify(notificationManipulator, times(1)).makeNotificationApplicationToStudent(any(Application.class), any(Notification.NotificationCause.class));
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

    @Test
    void signContract_Success() {
        Long contractId = 1L;
        Contract contract = createContract();
        Employer employer = createEmployer();
        Signature<Employer> signature = new Signature<>(null, employer, LocalDate.now(), contract);

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));
        when(signatureRepository.save(any(Signature.class))).thenReturn(signature);

        Optional<SignatureDTO> result = employerService.signContract(contractId);

        assertTrue(result.isPresent());
    }

    @Test
    void signContract_ContractNotFound() {
        Long contractId = 1L;
        when(contractRepository.findById(contractId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.signContract(contractId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Contract not found");
    }

    @Test
    void getSignaturesByContractId_HappyPath() {
        Contract contract = createContract();
        Signature<Employer> signature = new Signature<>(1L, createEmployer(), LocalDate.now(), contract);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(signatureRepository.findAllByContract(contract)).thenReturn(List.of(signature));

        List<SignatureDTO> result = employerService.getSignaturesByContractId(contract.getId());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(signature.getId());
        assertThat(result.get(0).getSignatureDate()).isEqualTo(signature.getSignatureDate());
    }

    @Test
    void getSignaturesByContractId_ContractNotFound() {
        assertThatThrownBy(() -> employerService.getSignaturesByContractId(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Contract not found");
    }

    @Test
    void getContractByApplicationId_Success() {
        Long applicationId = 1L;
        Application application = mock(Application.class);
        Contract contract = createContract();

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(contractRepository.findByApplication(application)).thenReturn(Optional.of(contract));

        Optional<ContractDTO> result = employerService.getContractByApplicationId(applicationId);

        assertTrue(result.isPresent());

        ContractDTO contractDTO = result.get();
        assertEquals(contract.getId(), contractDTO.getId());
        assertEquals(contract.getStartHour().toString(), contractDTO.getStartHour());
        assertEquals(contract.getEndHour().toString(), contractDTO.getEndHour());
        assertEquals(contract.getTotalHoursPerWeek(), contractDTO.getTotalHoursPerWeek());
        assertEquals(contract.getSalary(), contractDTO.getSalary(), 0.001);
        assertEquals(contract.getSupervisor(), contractDTO.getSupervisor());
        assertEquals(contract.getApplication().toDTO(), contractDTO.getApplication());
    }

    @Test
    void getContractByApplicationId_ApplicationNotFound() {
        Long applicationId = 1L;

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employerService.getContractByApplicationId(applicationId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Application not found");
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

    private Application createApplication() {
        Offer offer = createOffer(1L, createEmployer());
        return new Application(1L, createCv(), offer);
    }

    private Contract createContract() {
        DateTimeFormatter dtf = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("H:mm").toFormatter(Locale.ENGLISH);
        Application application = createApplication();
        return new Contract(1L, LocalTime.parse("08:00", dtf), LocalTime.parse("17:00", dtf), 40, 18.35, createSupervisor(), application);
    }
    private Supervisor createSupervisor(){
        return new Supervisor("super", "visor", "supervisor", "supervisor@email.com", "1234567890", "-123");
    }
}