package com.equipe4.audace.service;

import com.equipe4.audace.dto.ApplicationDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
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
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

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
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private StudentService studentService;

    @Test
    void getOffersByDepartmentAndStatus_happyPath() {
        Department mockedDepartment = mock(Department.class);
        List<Offer> offers = new ArrayList<>();

        Employer fakeEmployer = Employer.employerBuilder()
                .firstName("employer").lastName("employerman").email("email@gmail.com").password("password")
                .organisation("organisation").position("position").phone("phone").extension("extension")
                .address("address")
                .build();
        fakeEmployer.setId(1L);

        Offer fakeOffer = Offer.offerBuilder()
                .title("title")
                .description("description")
                .internshipStartDate(LocalDate.now())
                .internshipEndDate(LocalDate.now())
                .offerEndDate(LocalDate.now())
                .availablePlaces(2)
                .department(mockedDepartment)
                .employer(fakeEmployer)
                .build();
        fakeEmployer.getOffers().add(fakeOffer);

        for (int i = 0; i < 3; i++)
            offers.add(fakeOffer);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartmentAndStatus(mockedDepartment, Offer.Status.ACCEPTED)).thenReturn(offers);

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
        when(offerRepository.findAllByDepartmentAndStatus(mockedDepartment, Offer.Status.ACCEPTED)).thenReturn(new ArrayList<>());

        List<OfferDTO> result = studentService.getAcceptedOffersByDepartment(1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void createStudent() {
        DepartmentDTO departmentDTO = DepartmentDTO.departmentDTOBuilder()
                .id(1L)
                .code("GEN")
                .name("Génie")
                .build();

        StudentDTO studentDTO = StudentDTO.studentDTOBuilder()
                .id(1L)
                .firstName("student")
                .lastName("studentMan")
                .email("email@gmail.com")
                .password("password")
                .address("adress")
                .phone("1234567890")
                .studentNumber("2212895")
                .departmentDTO(departmentDTO)
                .build();

        when(studentRepository.save(any())).thenReturn(studentDTO.fromDTO());

        when(departmentRepository.findByCode(anyString())).thenReturn(Optional.of(studentDTO.getDepartmentDTO().fromDto()));

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
        DepartmentDTO departmentDTO = DepartmentDTO.departmentDTOBuilder()
                .id(1L).code("GEN").name("Génie")
                .build();
        StudentDTO studentDTO = StudentDTO.studentDTOBuilder()
                .id(1L)
                .firstName("student")
                .lastName("studentMan")
                .email("email@gmail.com")
                .password("password")
                .address("adress")
                .phone("1234567890")
                .studentNumber("2212895")
                .departmentDTO(departmentDTO)
                .build();

        Student student = studentDTO.fromDTO();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Act
        StudentDTO result = studentService.getStudentById(1L).orElseThrow();

        // Assert
        assertThat(result.getFirstName()).isEqualTo("student");
        assertThat(result.getLastName()).isEqualTo("studentMan");
        assertThat(result.getEmail()).isEqualTo("email@gmail.com");
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

        Cv cv = Cv.cvBuilder()
                .student(studentDTO.fromDTO())
                .fileName(fileName)
                .content(bytes)
                .build();
        cv.setId(2L);
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
        DepartmentDTO departmentDTO = DepartmentDTO.departmentDTOBuilder()
                .id(1L).code("GEN").name("Génie")
                .build();
        return StudentDTO.studentDTOBuilder()
                .id(1L)
                .firstName("student")
                .lastName("studentMan")
                .email("email@gmail.com")
                .password("password")
                .address("adress")
                .phone("1234567890")
                .studentNumber("2212895")
                .departmentDTO(departmentDTO)
                .build();
    }

    private MultipartFile createMockFile() {
        return new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "test data".getBytes()
        );
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
        Department department = new Department("GLO", "Génie logiciel");
        Employer employer = Employer.employerBuilder()
                .firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
                .organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
                .address("Class Service, Javatown, Qc H8N1C1")
                .build();
        employer.setId(1L);
        Student student = Student.studentBuilder()
                .firstname("student")
                .lastname("studentman")
                .email("email@email.com")
                .password("password")
                .address("address")
                .phone("phone")
                .studentNumber("matricule")
                .department(department)
                .build();
        student.setId(1L);

        Cv cv = new Cv();
        cv.setId(1L);

        Offer offer = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(employer).department(department)
                .build();
        offer.setId(1L);
        Application application = Application.applicationBuilder()
                .student(student)
                .cv(cv)
                .offer(offer)
                .build();
        ApplicationDTO applicationDTO = application.toDTO();

        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(cvRepository.findById(anyLong())).thenReturn(Optional.of(cv));
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));


        ApplicationDTO dto = studentService.createApplication(applicationDTO).get();

        assertThat(dto).isEqualTo(applicationDTO);
        verify(applicationRepository, times(1)).save(application);
    }
}
