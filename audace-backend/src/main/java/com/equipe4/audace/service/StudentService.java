package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<StudentDTO> createStudent(StudentDTO studentDTO) {
        if (studentDTO == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (studentRepository.findStudentByStudentNumber(studentDTO.getStudentNumber()).isPresent()) {
            throw new IllegalArgumentException("Student already exists");
        }
        if (studentRepository.findStudentByEmail(studentDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Student already exists");
        }
        Student student = studentRepository.save(studentDTO.fromDTO());
        return Optional.of(student.toDTO());
    }
}
