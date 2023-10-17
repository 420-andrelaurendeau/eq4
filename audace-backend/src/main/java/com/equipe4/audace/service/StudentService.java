package com.equipe4.audace.service;

import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.OfferStatus;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService extends GenericUserService<Student> {
    private final DepartmentRepository departmentRepository;
    private final OfferRepository offerRepository;
    private final StudentRepository studentRepository;
    private final CvRepository cvRepository;
    private final ApplicationRepository applicationRepository;

    public StudentService(
            SaltRepository saltRepository,
            DepartmentRepository departmentRepository,
            OfferRepository offerRepository,
            StudentRepository studentRepository,
            CvRepository cvRepository,
            ApplicationRepository applicationRepository
    ) {
        super(saltRepository);
        this.departmentRepository = departmentRepository;
        this.offerRepository = offerRepository;
        this.studentRepository = studentRepository;
        this.cvRepository = cvRepository;
        this.applicationRepository = applicationRepository;
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
        studentDTO.setDepartment(departmentOptional.get().toDTO());

        Student student = studentDTO.fromDTO();
        hashAndSaltPassword(student);

        return Optional.of(studentRepository.save(student).toDTO());
    }

    @Transactional
    public List<OfferDTO> getAcceptedOffersByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        List<Offer> offers = offerRepository.findAllByDepartmentAndOfferStatus(department, OfferStatus.ACCEPTED);

        return offers.stream().map(Offer::toDTO).toList();
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id).map(Student::toDTO);
    }

    @Transactional
    public Optional<CvDTO> saveCv(MultipartFile file, Long studentId) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student not found"));
        byte[] bytes;
        String fileName;

        try {
            bytes = file.getBytes();
            fileName = file.getOriginalFilename();
        } catch (IOException e) {
            throw new IllegalArgumentException("File cannot be read");
        }

        Cv cv = new Cv(null, student, bytes, fileName);
        return Optional.of(cvRepository.save(cv).toDTO());
    }

    public Optional<ApplicationDTO> createApplication(ApplicationDTO applicationDTO){
        if(applicationDTO == null) throw new IllegalArgumentException("Application cannot be null");

        Long cvId = applicationDTO.getCv().getId();
        Long offerId = applicationDTO.getOffer().getId();

        Cv cv = cvRepository.findById(cvId).orElseThrow(() -> new NoSuchElementException("Cv not found"));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        Application application = new Application(null, cv, offer);

        return Optional.of(applicationRepository.save(application).toDTO());
    }

    public List<CvDTO> getCvsByStudent(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        List<Cv> cvs = cvRepository.findAllByStudentId(studentId);

        if (cvs.isEmpty()) {
            throw new NoSuchElementException("No CVs found for student ID: " + studentId);
        }

        return cvs.stream().map(Cv::toDTO).toList();
    }
}
