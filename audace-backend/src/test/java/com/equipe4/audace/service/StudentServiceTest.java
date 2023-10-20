package com.equipe4.audace.service;

import com.equipe4.audace.dto.ApplicationDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @InjectMocks
    private StudentService studentService;

    @Test
    void getOffersByDepartmentAndStatus_happyPath() {
        Department mockedDepartment = mock(Department.class);
        List<Offer> offers = new ArrayList<>();

        Employer fakeEmployer = new Employer(
                null,
                "employer",
                "employerMan",
                "employer@email.com",
                "password",
                "organisation",
                "position",
                "123 Street Street",
                "1234567890",
                "-123"
        );

        Offer mockedOffer = mock(Offer.class);
        fakeEmployer.getOffers().add(mockedOffer);

        for (int i = 0; i < 3; i++)
            offers.add(mockedOffer);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartmentAndOfferStatus(mockedDepartment, Offer.OfferStatus.ACCEPTED)).thenReturn(offers);

        List<OfferDTO> result = studentService.getAcceptedOffersByDepartment(1L);

        assertThat(result.size()).isEqualTo(offers.size());
        assertThat(result).containsExactlyInAnyOrderElementsOf(offers.stream().map(Offer::toDTO).toList());
    }

    @Test
    void getOffersByDepartmentAndStatus_departmentNotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getAcceptedOffersByDepartment(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Department not found");
    }

    @Test
    void getOffersByDepartmentAndStatus_noOffers() {
        Department mockedDepartment = mock(Department.class);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartmentAndOfferStatus(mockedDepartment, Offer.OfferStatus.ACCEPTED)).thenReturn(new ArrayList<>());

        List<OfferDTO> result = studentService.getAcceptedOffersByDepartment(1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void createStudent() {
        StudentDTO studentDTO = createStudentDTO();

        when(studentRepository.save(any())).thenReturn(studentDTO.fromDTO());
        when(departmentRepository.findByCode(anyString())).thenReturn(Optional.of(studentDTO.getDepartment().fromDTO()));
        when(saltRepository.save(any())).thenReturn(mock(Salt.class));

        when(departmentRepository.findByCode(anyString())).thenReturn(Optional.of(studentDTO.getDepartment().fromDTO()));

        Optional<StudentDTO> optionalStudentDTO = studentService.createStudent(studentDTO, "420");

        assertThat(optionalStudentDTO).isPresent();
    }

    @Test
    void createStudentNullStudent() {
        assertThatThrownBy(() -> studentService.createStudent(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student cannot be null");
    }

    @Test
    void createStudentAlreadyExists() {
        StudentDTO studentDTO = createStudentDTO();

        when(studentRepository.findStudentByStudentNumberOrEmail(anyString(), anyString())).thenReturn(Optional.of(studentDTO.fromDTO()));

        assertThatThrownBy(() -> studentService.createStudent(studentDTO, "420"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student already exists");
    }

    @Test
    void createStudentDepartmentInvalid() {
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

        Cv cv = new Cv(null, studentDTO.fromDTO(), bytes, fileName);
        CvDTO expected = cv.toDTO();

        when(cvRepository.save(any())).thenReturn(cv);
        CvDTO result = studentService.saveCv(file, studentDTO.getId()).get();

        verify(cvRepository, times(1)).save(any());
        assertThat(result).isEqualTo(expected);
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

    @Test
    void saveCv_fileUnreadable() {
        MultipartFile file = new CustomMockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                null
        );

        StudentDTO studentDTO = createStudentDTO();
        when(studentRepository.findById(studentDTO.getId())).thenReturn(Optional.of(studentDTO.fromDTO()));

        assertThatThrownBy(() -> studentService.saveCv(file, studentDTO.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("File cannot be read");
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
        Department department = new Department(1L, "GEN", "Génie");
        Student student = new Student(
                1L,
                "student",
                "studentMan",
                "email@email.com",
                "password",
                "123 Street Street",
                "1234567890",
                "123456789",
                department
        );
        Cv cv = new Cv(1L, student, new byte[0], "fileName");
        Offer offer = new Offer(1L, "title", "description", LocalDate.now(), LocalDate.now(), LocalDate.now(), 0, department, mock(Employer.class));
        Application application = new Application(null, student, cv, offer);

        ApplicationDTO applicationDTO = application.toDTO();

        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(cvRepository.findById(anyLong())).thenReturn(Optional.of(cv));
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));

        ApplicationDTO dto = studentService.createApplication(applicationDTO).get();

        assertThat(dto).isEqualTo(applicationDTO);
        verify(applicationRepository, times(1)).save(application);
    }
    @Test
    public void getOffersStudentApplied() {
        Student student = mock(Student.class);
        Offer offer = mock(Offer.class);
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);
        List<Application> applications = new ArrayList<>();
        applications.add(new Application(1L, student, mock(Cv.class), offer));

        when(applicationRepository.findApplicationsByStudentId(anyLong())).thenReturn(applications);

        List<OfferDTO> result = studentService.getOffersStudentApplied(1L);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result).containsExactlyInAnyOrderElementsOf(offers.stream().map(Offer::toDTO).toList());
    }
    @Test
    public void getOffersStudentApplied_isNull() {
        assertThatThrownBy(() -> studentService.getOffersStudentApplied(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID cannot be null");
    }
}
