package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    StudentRepository studentRepository;
    public void createStudent(StudentDTO studentDTO) {
        studentRepository.save(studentDTO.fromDTO());
    }
}
