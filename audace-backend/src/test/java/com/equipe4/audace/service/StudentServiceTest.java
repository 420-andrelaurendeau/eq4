package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private DepartmentRepository departmentRepository;
    @Mock
    private OfferRepository offerRepository;
    @InjectMocks
    private StudentService studentService;

    @Test
    void getOffersByDepartmentAndStatus_happyPath() {
        Department mockedDepartment = mock(Department.class);
        List<Offer> offers = new ArrayList<>();

        Employer fakeEmployer = new Employer(
                1L,
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
        fakeEmployer.setId(1L);

        Offer fakeOffer = new Offer(
                "title",
                "description",
                null,
                null,
                null,
                fakeEmployer,
                mockedDepartment
        );
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
        StudentDTO studentDTO = new StudentDTO(1L,"student", "studentMan", "email@gmail.com", "adress", "1234567890", "password", "2212895", new DepartmentDTO(1L, "GEN", "Génie"));

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
        StudentDTO studentDTO = new StudentDTO(1L, "student", "studentMan", "email@gmail.com", "adress", "1234567890", "password", "2212895", new DepartmentDTO(1L, "GEN", "Génie"));
        when(studentRepository.findStudentByStudentNumberOrEmail(anyString(), anyString())).thenReturn(Optional.of(studentDTO.fromDTO()));

        assertThatThrownBy(() -> studentService.createStudent(studentDTO, "420"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student already exists");
    }

    @Test
    void createStudentDepartmentInvalid() {
        StudentDTO studentDTO = new StudentDTO(1L, "student", "studentMan", "email@gmail.com", "adress", "1234567890", "password", "2212895", new DepartmentDTO(1L, "GEN", "Génie"));
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
}
