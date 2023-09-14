package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.OfferDTO;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.DepartmentRepository;
import com.equipe4.audace.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

    public void createStudent(StudentDTO studentDTO) {
        studentRepository.save(studentDTO.fromDTO());
    }

    @Transactional
    public List<OfferDTO> getOffersByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        List<Offer> offers = offerRepository.findAllByDepartment(department);

        return offers.stream().map(Offer::toDto).toList();
    }
}
