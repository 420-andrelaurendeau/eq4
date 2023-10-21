package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private CvRepository cvRepository;
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private ManagerService managerService;

    @Test
    public void acceptOffer() {
        Employer employer = mock(Employer.class);
        Department department = mock(Department.class);
        Offer offer = new Offer(1L, "Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any())).thenReturn(offer);

        managerService.acceptOffer(1L);


        assert(offer.getOfferStatus() == Offer.OfferStatus.ACCEPTED);
    }

    @Test
    public void acceptOffer_InvalidId() {
        when(offerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.acceptOffer(1L));
    }

    @Test
    public void refuseOffer() {
        Employer employer = mock(Employer.class);
        Department department = mock(Department.class);
        Offer offer = new Offer(1L, "Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, department, employer);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any())).thenReturn(offer);

        managerService.refuseOffer(1L);

        assert(offer.getOfferStatus() == Offer.OfferStatus.REFUSED);
    }

    @Test
    public void refuseOffer_Invalid_Id() {
        when(offerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.refuseOffer(1L));
    }

    @Test
    void getOffersByDepartment_happyPath() {
        Department mockedDepartment = mock(Department.class);
        List<Offer> offers = new ArrayList<>();

        Employer fakeEmployer = new Employer(1L, "Employer1", "Employer1", "asd@email.com", "password", "Organisation1", "Position1", "123-456-7890", "12345", "Class Service, Javatown, Qc H8N1C1");
        Offer fakeOffer = new Offer(1L, "Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, mockedDepartment, fakeEmployer);

        fakeEmployer.getOffers().add(fakeOffer);

        for (int i = 0; i < 3; i++)
            offers.add(fakeOffer);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartment(mockedDepartment)).thenReturn(offers);

        List<OfferDTO> result = managerService.getOffersByDepartment(1L);

        assertThat(result.size()).isEqualTo(offers.size());
        assertThat(result).containsExactlyInAnyOrderElementsOf(offers.stream().map(Offer::toDTO).toList());
    }

    @Test
    void getOffersByDepartment_departmentNotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.getOffersByDepartment(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Department not found");
    }

    @Test
    void getOffersByDepartment_noOffers() {
        Department mockedDepartment = mock(Department.class);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartment(mockedDepartment)).thenReturn(new ArrayList<>());

        List<OfferDTO> result = managerService.getOffersByDepartment(1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void findManagerById_happyPathTest() {
        // Arrange
        Department department = mock(Department.class);
        Manager manager = new Manager(1L, "manager", "managerman", "manager@email.com", "password", "yeete", "1234567890", department);

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
        listCvs.add(new Cv(null, student, new byte[10], "cv"));

        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(listCvs);

        List<CvDTO> result = managerService.getCvsByDepartment(1L);

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void getCvsByDepartment_invalidDepartmentId() {
        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(new ArrayList<>());

        assertThat(managerService.getCvsByDepartment(1L).size()).isEqualTo(0);
    }

    @Test
    public void getCvsByDepartment_noCvs() {
        List<Cv> listCvs = new ArrayList<>();

        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(listCvs);

        List<CvDTO> result = managerService.getCvsByDepartment(1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void acceptCv() {
        Student student = mock(Student.class);
        Cv cv = new Cv(null, student, "Monkey Enthusiast needs more sleep".getBytes(), "cv");
        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(cvRepository.save(any())).thenReturn(cv);

        Optional<CvDTO> cvDTO = managerService.acceptCv(1L);
        if (cvDTO.isPresent()) {
            assert(cvDTO.get().getCvStatus() == Cv.CvStatus.ACCEPTED);
        }
        else {
            assert(false);
        }
    }

    @Test
    public void acceptCv_InvalidId() {
        when(cvRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.acceptCv(1L));
    }

    @Test
    public void refuseCv() {
        Student student = mock(Student.class);
        Cv cv = new Cv(null, student, "Monkey Enthusiast needs more sleep".getBytes(), "cv");
        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(cvRepository.save(any())).thenReturn(cv);

        Optional<CvDTO> cvDTO = managerService.refuseCv(1L);
        if (cvDTO.isPresent()) {
            assert(cvDTO.get().getCvStatus() == Cv.CvStatus.REFUSED);
        }
        else {
            assert(false);
        }
    }

    @Test
    public void refuseCv_Invalid_Id() {
        when(cvRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.refuseCv(1L));
    }

    @Test
    public void createContract_HappyPath(){
        // Arrange
        ContractDTO contractDTO = createContract().toDTO();

        when(contractRepository.save(any(Contract.class))).thenReturn(contractDTO.fromDTO());

        ContractDTO dto = managerService.createContract(contractDTO).get();

        assertThat(dto.equals(contractDTO));
        verify(contractRepository, times(1)).save(contractDTO.fromDTO());
    }
    @Test
    public void createContract_NullContract(){
        assertThatThrownBy(() -> managerService.createContract(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Contract cannot be null");
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
    private ApplicationDTO createApplicationDTO(Offer offer) {
        CvDTO cvDTO = createCv().toDTO();
        return new ApplicationDTO(1L, offer.toDTO(), cvDTO);
    }
    private Cv createCv(){
        MultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "test data".getBytes());
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file");
        }
        Student student = createStudentDTO().fromDTO();
        return new Cv(1L, student, bytes, "cv");
    }

    private Contract createContract(){
        Employer employer = createEmployerDTO().fromDTO();
        Offer offer = createOffer();
        Application application = createApplicationDTO(offer).fromDTO();
        return new Contract(1L, "Construction", LocalTime.parse("08:00"),LocalTime.parse("17:00"), 40, 18.35, "TODO", employer, application);
    }
}