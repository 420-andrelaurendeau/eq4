package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final OfferRepository offerRepository;
    private final DepartmentRepository departmentRepository;

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
        List<Offer> offers = offerRepository.findAllByDepartment(department);

        return offers.stream().map(Offer::toDto).toList();
    }

    @Transactional
    public Optional<CvDTO> saveCv(MultipartFile file, Long uploaderId) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        Student student = studentRepository.findById(uploaderId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));

        byte[] bytes;
        String name;

        try {
            bytes = file.getBytes();
            name = file.getName();
        } catch (IOException e) {
            throw new IllegalArgumentException("File cannot be read");
        }

        Cv cv = new Cv(student, name, bytes);
        List<Cv> cvs = new ArrayList<>(student.getCvs());
        cvs.add(cv);
        student.setCvs(cvs);

        student = studentRepository.save(student);

        cvs = new ArrayList<>(student.getCvs());
        return Optional.of(cvs.get(cvs.size() - 1).toDto());
    }
}
