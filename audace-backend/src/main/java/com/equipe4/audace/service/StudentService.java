package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final OfferRepository offerRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, OfferRepository offerRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.offerRepository = offerRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Optional<StudentDTO> createStudent(StudentDTO studentDTO, String departmentCode) {
        if (studentDTO == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        Optional<Student> studentOptional =
                studentRepository.findStudentByStudentNumberOrEmail(studentDTO.getStudentNumber(), studentDTO.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalArgumentException("Student already exists");
        }
        Optional<Department> departmentOptional = departmentRepository.findByCode(departmentCode);

        if (departmentOptional.isEmpty()) {
            throw new NoSuchElementException("Department not found");
        }
        studentDTO.setDepartment(departmentOptional.get().toDto());
        Student student = studentRepository.save(studentDTO.fromDTO());
        return Optional.of(student.toDTO());
    }

    @Transactional
    public List<OfferDTO> getOffersByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        //List<Offer> offers = offerRepository.findAllByDepartment(department);
        return offerRepository.findAllByDepartment(department).stream().map(OfferDTO::new).toList();
    }
}
