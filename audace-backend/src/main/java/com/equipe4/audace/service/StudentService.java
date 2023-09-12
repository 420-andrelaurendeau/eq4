package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        List<Offer> offers = offerRepository.findAllByDepartment(department);

        return offers.stream().map(Offer::toDto).toList();
    }
}
