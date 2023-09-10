package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;
    public Optional<Student> createStudent(StudentDTO studentDTO) {
        if (studentDTO == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (null != studentRepository.findStudentByStudentNumber(studentDTO.getStudentNumber())) {
            throw new IllegalArgumentException("Student already exists");
        }
        if (null != studentRepository.findStudentByEmail(studentDTO.getEmail())) {
            throw new IllegalArgumentException("Student already exists");
        }
        return Optional.of(studentRepository.save(studentDTO.fromDTO()));
    }
}
