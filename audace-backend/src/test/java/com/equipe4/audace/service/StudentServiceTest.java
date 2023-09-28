package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @InjectMocks
    private StudentService studentService;

    @Test
    void createStudent() {
        StudentDTO studentDTO = new StudentDTO(1L, "student", "studentMan", "email@gmail.com", "adress", "1234567890", "password", "2212895", new DepartmentDTO(1L, "GEN", "Génie"));

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
}
