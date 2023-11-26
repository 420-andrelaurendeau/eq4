package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.contract.SignatureDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.Supervisor;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.contract.Signature;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.StudentSessionRepository;
import com.equipe4.audace.repository.signature.SignatureRepository;
import com.equipe4.audace.utils.NotificationManipulator;
import com.equipe4.audace.utils.SessionManipulator;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private CvRepository cvRepository;
    @Mock
    private SaltRepository saltRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private SessionManipulator sessionManipulator;
    @Mock
    private StudentSessionRepository studentSessionRepository;
    @Mock
    private NotificationManipulator notificationManipulator;
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SignatureRepository signatureRepository;
    @InjectMocks
    private StudentService studentService;

    @Test
    void getOffersByDepartmentAndStatus_happyPath() {
        Department department = createDepartment();
        Employer employer = createEmployer();

        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusMonths(6));
        List<Offer> offers = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            Offer offer = createOffer(Long.valueOf(i+1), createEmployer());
            offer.setOfferStatus(Offer.OfferStatus.ACCEPTED);
            offers.add(offer);
        }

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(offerRepository.findAllByDepartmentAndOfferStatus(department, Offer.OfferStatus.ACCEPTED)).thenReturn(offers);
        when(sessionManipulator.removeOffersNotInSession(offers, session.getId())).thenReturn(offers);

        List<OfferDTO> result = studentService.getAcceptedOffersByDepartment(1L, session.getId());

        assertThat(result.size()).isEqualTo(offers.size());
        assertThat(result).containsExactlyInAnyOrderElementsOf(offers.stream().map(Offer::toDTO).toList());
    }

    @Test
    void getOffersByDepartmentAndStatus_departmentNotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getAcceptedOffersByDepartment(1L, 1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Department not found");
    }

    @Test
    void getOffersByDepartmentAndStatus_noOffers() {
        Department mockedDepartment = mock(Department.class);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartmentAndOfferStatus(mockedDepartment, Offer.OfferStatus.ACCEPTED)).thenReturn(new ArrayList<>());

        List<OfferDTO> result = studentService.getAcceptedOffersByDepartment(1L, 1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void createStudent_HappyPath() {
        StudentDTO studentDTO = createStudentDTO();

        when(studentRepository.save(any())).thenReturn(studentDTO.fromDTO());
        when(departmentRepository.findByCode(anyString())).thenReturn(Optional.of(studentDTO.getDepartment().fromDTO()));
        when(saltRepository.save(any())).thenReturn(mock(Salt.class));

        when(departmentRepository.findByCode(anyString())).thenReturn(Optional.of(studentDTO.getDepartment().fromDTO()));

        Optional<StudentDTO> optionalStudentDTO = studentService.createStudent(studentDTO, "420");

        assertThat(optionalStudentDTO).isPresent();
    }

    @Test
    void createStudent_NullStudent() {
        assertThatThrownBy(() -> studentService.createStudent(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student cannot be null");
    }

    @Test
    void createStudent_DepartmentInvalid() {
        StudentDTO studentDTO = createStudentDTO();

        when(departmentRepository.findByCode(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.createStudent(studentDTO, "INVALIDE DUH"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Department not found");
    }

    @Test
    public void findStudentById_happyPathTest() {
        // Arrange
        Student student = createStudentDTO().fromDTO();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Act
        StudentDTO result = studentService.getStudentById(1L).orElseThrow();

        // Assert
        assertThat(result.getFirstName()).isEqualTo("student");
        assertThat(result.getLastName()).isEqualTo("studentMan");
        assertThat(result.getEmail()).isEqualTo("email@email.com");
    }

    @Test
    public void findStudentById_notFoundTest() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<StudentDTO> result = studentService.getStudentById(1L);

        // Assert
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void saveCv_happyPath() {
        MultipartFile file = createMockFile();

        StudentDTO studentDTO = createStudentDTO();
        when(studentRepository.findById(studentDTO.getId())).thenReturn(Optional.of(studentDTO.fromDTO()));

        byte[] bytes;
        String fileName;

        try {
            bytes = file.getBytes();
            fileName = file.getName();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file");
        }

        Cv cv = new Cv(null, fileName, bytes, studentDTO.fromDTO());
        CvDTO expected = cv.toDTO();

        when(cvRepository.save(any())).thenReturn(cv);
        CvDTO result = studentService.saveCv(file, studentDTO.getId()).get();

        verify(cvRepository, times(1)).save(any());
        assertThat(result).isEqualTo(expected);
        verify(notificationManipulator, times(1)).makeNotificationCvToAllManagersByDepartment(any(), any());
    }

    @Test
    void saveCv_studentNotFound() {
        MultipartFile file = createMockFile();

        StudentDTO studentDTO = createStudentDTO();
        when(studentRepository.findById(studentDTO.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.saveCv(file, studentDTO.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Student not found");
    }

    @Test
    void saveCv_fileNull() {
        StudentDTO studentDTO = createStudentDTO();

        assertThatThrownBy(() -> studentService.saveCv(null, studentDTO.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("File cannot be null");
    }

    private StudentDTO createStudentDTO() {
        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "GEN", "Génie");
        return new StudentDTO(1L, "student", "studentMan", "email@email.com", "123 Street street", "1234567890", "123456789", "studentNumber", departmentDTO);
    }

    private MultipartFile createMockFile() {
        return new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "test data".getBytes());
    }

    @Test
    void getCvsByStudent() {
        StudentDTO studentDTO = createStudentDTO();
        Cv cv1 = mock(Cv.class);
        Cv cv2 = mock(Cv.class);

        List<Cv> cvs = new ArrayList<>();
        cvs.add(cv1);
        cvs.add(cv2);

        when(cvRepository.findAllByStudentId(studentDTO.getId())).thenReturn(cvs);
        List<CvDTO> result = studentService.getCvsByStudent(studentDTO.getId());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactlyInAnyOrderElementsOf(cvs.stream().map(Cv::toDTO).toList());
    }

    private static class CustomMockMultipartFile extends MockMultipartFile {
        public CustomMockMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
            super(name, originalFilename, contentType, content);
        }

        // To test the IOException
        @Override
        public byte[] getBytes() throws IOException {
            throw new IOException();
        }
    }

    @Test
    public void createApplication_HappyPath(){
        Offer offer = createOffer(1L, createEmployer());
        Cv cv = createCv();
        Application application = new Application(null, cv, offer);

        ApplicationDTO applicationDTO = application.toDTO();

        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        when(cvRepository.findById(anyLong())).thenReturn(Optional.of(cv));
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));

        ApplicationDTO dto = studentService.createApplication(applicationDTO).get();

        assertThat(dto).isEqualTo(applicationDTO);
        verify(applicationRepository, times(1)).save(application);
        verify(notificationManipulator, times(1)).makeNotificationApplicationToOfferEmployer(any(), any());
    }

    @Test
    public void createApplication_NullApplication(){
        assertThatThrownBy(() -> studentService.createApplication(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Application cannot be null");
    }

    @Test
    public void getApplicationsByStudentIdAndSessionId_HappyPath() {
        Employer employer = createEmployer();
        Student student = createStudent();

        List<Application> applications = new ArrayList<>();
        applications.add(new Application(1L, createCv(), createOffer(1L, employer)));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(applicationRepository.findApplicationsByCv_Student(any(Student.class))).thenReturn(applications);
        when(sessionManipulator.removeApplicationsNotInSession(applications, 1L)).thenReturn(applications);

        List<ApplicationDTO> result = studentService.getApplicationsByStudentIdAndSessionId(1L, 1L);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result).containsExactlyInAnyOrderElementsOf(applications.stream().map(Application::toDTO).toList());
    }
    @Test
    public void getApplicationsByStudentIdAndSessionId_isNull() {
        assertThatThrownBy(() -> studentService.getApplicationsByStudentIdAndSessionId(null, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID cannot be null");
    }

    @Test
    void signContract_Success() {
        Long contractId = 1L;
        Contract contract = createContract();
        Student student = createStudent();
        Signature<Student> signature = new Signature<>(null, student, LocalDate.now(), contract);

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));
        when(studentRepository.findByCv(any(Cv.class))).thenReturn(Optional.of(student));
        when(signatureRepository.save(any(Signature.class))).thenReturn(signature);

        Optional<SignatureDTO> result = studentService.signContract(contractId);

        assertTrue(result.isPresent());
    }

    @Test
    void signContract_ContractNotFound() {
        Long contractId = 1L;
        when(contractRepository.findById(contractId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.signContract(contractId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Contract not found");
    }

    @Test
    void signContract_StudentNotFound() {
        Long contractId = 1L;
        Contract contract = createContract();

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));
        when(studentRepository.findByCv(any(Cv.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.signContract(contractId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Student not found");
    }

    @Test
    void getSignaturesByContractId_HappyPath() {
        Contract contract = createContract();
        Signature<Student> signature = new Signature<>(1L, createStudent(), LocalDate.now(), contract);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(signatureRepository.findAllByContract(contract)).thenReturn(List.of(signature));

        List<SignatureDTO> result = studentService.getSignaturesByContractId(contract.getId());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(signature.getId());
        assertThat(result.get(0).getSignatureDate()).isEqualTo(signature.getSignatureDate());
    }

    @Test
    void getSignaturesByContractId_ContractNotFound() {
        assertThatThrownBy(() -> studentService.getSignaturesByContractId(1L))
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

        Optional<ContractDTO> result = studentService.getContractByApplicationId(applicationId);

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

        assertThatThrownBy(() -> studentService.getContractByApplicationId(applicationId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Application not found");
    }

    @Test
    void findContractById_HappyPath() {
        Contract mockContract = createContract();
        when(contractRepository.findById(anyLong())).thenReturn(Optional.of(mockContract));

        Optional<ContractDTO> result = studentService.findContractById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(mockContract.getId());
        assertThat(result.get().getStartHour()).isEqualTo(mockContract.getStartHour().toString());
        assertThat(result.get().getEndHour()).isEqualTo(mockContract.getEndHour().toString());
        assertThat(result.get().getTotalHoursPerWeek()).isEqualTo(mockContract.getTotalHoursPerWeek());
        assertThat(result.get().getSalary()).isEqualTo(mockContract.getSalary());
        assertThat(result.get().getSupervisor()).isEqualTo(mockContract.getSupervisor());
        assertThat(result.get().getApplication()).isEqualTo(mockContract.getApplication().toDTO());
    }

    @Test
    void findContractById_NotFound() {
        when(contractRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.findContractById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Contract not found");
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
