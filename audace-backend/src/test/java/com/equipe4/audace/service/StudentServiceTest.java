package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
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
import java.io.InputStream;
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
        assertThat(result).containsExactlyInAnyOrderElementsOf(offers.stream().map(OfferDTO::new).toList());
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
        StudentDTO studentDTO = new StudentDTO(1L, "student", "studentMan", "email@gmail.com", "adress", "1234567890", "password", "2212895", new DepartmentDTO(1L, "GEN", "Génie"), new ArrayList<>());

        when(studentRepository.save(any())).thenReturn(studentDTO.fromDTO());

        when(departmentRepository.findByCode(anyString())).thenReturn(Optional.of(studentDTO.getDepartment().fromDto()));

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
        StudentDTO studentDTO = new StudentDTO(1L, "student", "studentMan", "email@gmail.com", "adress", "1234567890", "password", "2212895", new DepartmentDTO(1L, "GEN", "Génie"));
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
        String name;

        try {
            bytes = file.getBytes();
            name = file.getName();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file");
        }

        Cv cv = new Cv(2L, studentDTO.fromDTO(), name, bytes);
        CvDTO expected = cv.toDto();

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
        return new StudentDTO(
                1L,
                "student",
                "studentMan",
                "email@gmail.com",
                "adress",
                "1234567890",
                "password",
                "2212895",
                new DepartmentDTO(1L, "GEN", "Génie"),
                new ArrayList<>()
        );
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
}
