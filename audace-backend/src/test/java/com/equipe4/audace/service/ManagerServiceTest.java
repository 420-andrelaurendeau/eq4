package com.equipe4.audace.service;

import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.Supervisor;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.contract.Signature;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.utils.NotificationManipulator;
import com.equipe4.audace.utils.SessionManipulator;
import jakarta.persistence.EntityNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CvRepository cvRepository;
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private SessionManipulator sessionManipulator;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private NotificationManipulator notificationManipulator;
    @InjectMocks
    private ManagerService managerService;

    @Test
    public void acceptOffer() {
        Employer employer = createEmployer();
        Department department = new Department(1L, "code", "name");
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
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department
        );
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer1));
        when(offerRepository.save(any())).thenReturn(offer1);
        when(sessionManipulator.isOfferInCurrentSession(offer1)).thenReturn(true);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        managerService.acceptOffer(1L, 1L);

        verify(notificationManipulator, times(1)).makeNotificationOfferToAllStudents(any(), any());
        verify(notificationManipulator, times(1)).makeNotificationOfferToOfferEmployer(any(), any());

        assert(offer1.getOfferStatus() == Offer.OfferStatus.ACCEPTED);
    }

    @Test
    public void acceptOffer_InvalidId() {
        when(offerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.acceptOffer(1L, 1L));
    }

    @Test
    public void acceptOffer_wrongDepartment() {
        Employer employer = mock(Employer.class);
        Department department = new Department(1L, "code", "name");
        Department department2 = new Department(2L, "code2", "name2");
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
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department2
        );
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer1));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> managerService.acceptOffer(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
    }

    @Test
    public void refuseOffer() {
        Employer employer = mock(Employer.class);
        Department department = new Department(1L, "code", "name");
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
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department
        );
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer1));
        when(offerRepository.save(any())).thenReturn(offer1);
        when(sessionManipulator.isOfferInCurrentSession(offer1)).thenReturn(true);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        managerService.refuseOffer(1L, 1L);

        verify(notificationManipulator, times(1)).makeNotificationOfferToOfferEmployer(offer1, Notification.NotificationCause.UPDATED);

        assert(offer1.getOfferStatus() == Offer.OfferStatus.REFUSED);
    }

    @Test
    public void refuseOffer_Invalid_Id() {
        when(offerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.refuseOffer(1L, 1L));
    }

    @Test
    public void refuseOffer_wrongDepartment() {
        Employer employer = mock(Employer.class);
        Department department = new Department(1L, "code", "name");
        Department department2 = new Department(2L, "code2", "name2");
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
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department2
        );
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer1));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> managerService.refuseOffer(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
    }

    @Test
    void getOffersByDepartment_happyPath() {
        Department mockedDepartment = mock(Department.class);
        List<Offer> offers = new ArrayList<>();

        Employer fakeEmployer = mock(Employer.class);

        Offer fakeOffer = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                mockedDepartment,
                fakeEmployer
        );

        for (int i = 0; i < 3; i++)
            offers.add(fakeOffer);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartment(mockedDepartment)).thenReturn(offers);
        when(sessionManipulator.removeOffersNotInSession(offers, 1L)).thenReturn(offers);

        List<OfferDTO> result = managerService.getOffersByDepartmentIdAndSessionId(1L, 1L);

        assertThat(result.size()).isEqualTo(offers.size());
        assertThat(result).containsExactlyInAnyOrderElementsOf(offers.stream().map(Offer::toDTO).toList());
    }

    @Test
    void getOffersByDepartment_departmentNotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.getOffersByDepartmentIdAndSessionId(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Department not found");
    }

    @Test
    void getOffersByDepartment_noOffers() {
        Department mockedDepartment = mock(Department.class);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartment(mockedDepartment)).thenReturn(new ArrayList<>());

        List<OfferDTO> result = managerService.getOffersByDepartmentIdAndSessionId(1L, 1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void findManagerById_happyPathTest() {
        // Arrange
        Department department = mock(Department.class);
        Manager manager = new Manager(
                1L,
                "manager",
                "managerman",
                "manager@email.com",
                "password",
                "1234567890",
                "123456789",
                department
        );

        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));

        // Act
        ManagerDTO result = managerService.getManagerById(1L).orElseThrow();

        // Assert
        assertThat(result.getFirstName()).isEqualTo("manager");
        assertThat(result.getLastName()).isEqualTo("managerman");
        assertThat(result.getEmail()).isEqualTo("manager@email.com");
    }

    @Test
    public void findManagerById_notFoundTest() {
        // Arrange
        when(managerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<ManagerDTO> result = managerService.getManagerById(1L);

        // Assert
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void getCvsByDepartment_happyPath() {
        Department mockedDepartment = mock(Department.class);
        Student student = new Student(1L, "firstName", "lastName", "email", "password", "address", "phone", "studentNumber", mockedDepartment);
        List<Cv> listCvs = new ArrayList<>();
        listCvs.add(new Cv(null, "cv", new byte[10], student));

        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(listCvs);
        when(sessionManipulator.removeCvsBelongingToStudentNotInSession(any(), anyLong())).thenReturn(listCvs);

        List<CvDTO> result = managerService.getCvsByDepartment(1L, 1L);

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void getCvsByDepartment_invalidDepartmentId() {
        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(new ArrayList<>());

        assertThat(managerService.getCvsByDepartment(1L, 1L).size()).isEqualTo(0);
    }

    @Test
    public void getCvsByDepartment_noCvs() {
        List<Cv> listCvs = new ArrayList<>();

        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(listCvs);

        List<CvDTO> result = managerService.getCvsByDepartment(1L, 1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void acceptCv() {
        Cv cv = createCv();
        Department department = createDepartment();
        Manager manager = new Manager(1L, "firstName", "lastName", "email", "password", "address", "phone", department);

        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(cvRepository.save(any())).thenReturn(cv);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        Optional<CvDTO> cvDTO = managerService.acceptCv(1L, 1L);
        if (cvDTO.isPresent()) {
            assert(cvDTO.get().getCvStatus() == Cv.CvStatus.ACCEPTED);
        }
        else {
            assert(false);
        }
        verify(notificationManipulator, times(1)).makeNotificationCvToCvStudent(any(), any());
    }

    @Test
    public void acceptCv_InvalidId() {
        when(cvRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.acceptCv(1L, 1L));
    }

    @Test
    public void acceptCv_wrongDepartment() {
        Cv cv = createCv();
        Department department2 = new Department(3L, "code2", "name2");
        Manager manager = new Manager(1L, "firstName", "lastName", "email", "password", "address", "phone", department2);

        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> managerService.acceptCv(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
    }

    @Test
    public void refuseCv_HappyPath() {
        Cv cv = createCv();
        Department department = createDepartment();
        Manager manager = new Manager(1L, "firstName", "lastName", "email", "password", "address", "phone", department);

        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(cvRepository.save(any())).thenReturn(cv);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        Optional<CvDTO> cvDTO = managerService.refuseCv(1L, 1L);
        if (cvDTO.isPresent()) {
            assert(cvDTO.get().getCvStatus() == Cv.CvStatus.REFUSED);
        }
        else {
            assert(false);
        }
        verify(notificationManipulator, times(1)).makeNotificationCvToCvStudent(any(), any());
    }
    @Test
    public void refuseCv_Invalid_Id() {
        when(cvRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.refuseCv(1L, 1L));
    }
    @Test
    public void refuseCv_wrongDepartment() {
        Cv cv = createCv();
        Department department2 = new Department(3L, "code2", "name2");
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department2
        );
        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> managerService.refuseCv(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
    }
    @Test
    public void getAcceptedApplicationsByDepartment_happyPath() {
        List<Application> applications = new ArrayList<>();
        Department department = new Department(1L, "code", "name");
        Student student = new Student(1L, "firstName", "lastName", "email", "password", "address", "phone", "studentNumber", department);
        Cv cv = new Cv(1L, "cv.pdf", "Monkey Enthusiast has OK sleep now".getBytes(), student);
        Employer employer = new Employer(1L, "Employer1", "Employer1", "email", "password", "Organisation1", "Position1", "123-456-7890", "12345", "Class Service, Javatown, Qc H8N1C1");
        Offer offer = new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, department, employer);
        applications.add(new Application(1L, cv, offer));

        when(applicationRepository.findAllByApplicationStatusAndAndOffer_Department(any(), any(Department.class))).thenReturn(applications);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(new Manager(1L, "firstName", "lastName", "email", "password", "address", "phone", department)));
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));

        List<ApplicationDTO> result = managerService.getAcceptedApplicationsByManagerIdAndDepartmentId(1L, 1L);

        assertThat(result.size()).isEqualTo(1);
    }
    @Test
    public void getAcceptedApplicationsByManagerIdAndDepartmentId_invalidManager() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department(1L, "code", "name")));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.getAcceptedApplicationsByManagerIdAndDepartmentId(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Manager not found");
    }
    @Test
    public void getAcceptedApplicationsByDepartment_invalidDepartment() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.getAcceptedApplicationsByManagerIdAndDepartmentId(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Department not found");
    }
    @Test
    public void getDepartmentByManager_happyPath() {
        Department department = new Department(1L, "code", "name");
        Manager manager = new Manager(1L, "firstName", "lastName", "email", "password", "address", "phone", department);

        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        DepartmentDTO result = managerService.getDepartmentByManager(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCode()).isEqualTo("code");
        assertThat(result.getName()).isEqualTo("name");
    }
    @Test
    public void getDepartmentByManager_invalidManager() {
        assertThatThrownBy(() -> managerService.getDepartmentByManager(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Manager not found with ID: " + 1L);
    }

    @Test
    public void createContract_HappyPath(){
        // Arrange
        ContractDTO contractDTO = createContract().toDTO();

        when(contractRepository.save(any(Contract.class))).thenReturn(contractDTO.fromDTO());

        ContractDTO dto = managerService.createContract(contractDTO).get();

        assertEquals(dto, contractDTO);
        verify(contractRepository, times(1)).save(contractDTO.fromDTO());
    }
    @Test
    public void createContract_NullContract(){
        assertThatThrownBy(() -> managerService.createContract(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Contract cannot be null");
    }

    @Test
    public void signContract_HappyPath(){
        Manager manager = createManager();
        Contract contract = createContract();
        contract.setManagerSignature(new Signature<>(manager, LocalDate.now()));

        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));
        when(contractRepository.findById(anyLong())).thenReturn(Optional.of(contract));
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);

        ContractDTO contractDTO = managerService.signContract(1L, 1L).get();
        assertEquals(contractDTO.fromDTO().getManagerSignature(), contract.getManagerSignature());
    }

    @Test
    public void signContract_InvalidManagerId(){
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(contractRepository.findById(anyLong())).thenReturn(Optional.of(createContract()));

        assertThatThrownBy(() -> managerService.signContract(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Manager not found");
    }

    @Test
    public void signContract_InvalidContractId(){
        when(contractRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.signContract(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Contract not found");
    }

    @Test
    public void signContract_WrongDepartment(){
        Manager manager = createManager();
        manager.setDepartment(new Department(2L, "code2", "name2"));
        Contract contract = createContract();

        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));
        when(contractRepository.findById(anyLong())).thenReturn(Optional.of(contract));

        assertThatThrownBy(() -> managerService.signContract(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
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

    private Manager createManager() {
        Department department = createDepartment();
        return new Manager(1L, "firstName", "lastName", "email", "password", "address", "phone", department);
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