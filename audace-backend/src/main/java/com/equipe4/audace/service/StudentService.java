package com.equipe4.audace.service;

import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.contract.Signature;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.OfferStatus;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.model.session.StudentSession;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.StudentSessionRepository;
import com.equipe4.audace.utils.NotificationManipulator;
import com.equipe4.audace.utils.SessionManipulator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class StudentService extends GenericUserService<Student> {
    private final DepartmentRepository departmentRepository;
    private final OfferRepository offerRepository;
    private final StudentRepository studentRepository;
    private final CvRepository cvRepository;
    private final ApplicationRepository applicationRepository;
    private final StudentSessionRepository studentSessionRepository;
    private final SessionManipulator sessionManipulator;
    private final ContractRepository contractRepository;
    private final NotificationManipulator notificationManipulator;


    public StudentService(SaltRepository saltRepository, DepartmentRepository departmentRepository, OfferRepository offerRepository,
                          StudentRepository studentRepository, CvRepository cvRepository, ApplicationRepository applicationRepository,
                          StudentSessionRepository studentSessionRepository, SessionManipulator sessionManipulator,
                          ContractRepository contractRepository, NotificationManipulator notificationManipulator) {
        super(saltRepository);
        this.departmentRepository = departmentRepository;
        this.offerRepository = offerRepository;
        this.studentRepository = studentRepository;
        this.cvRepository = cvRepository;
        this.applicationRepository = applicationRepository;
        this.studentSessionRepository = studentSessionRepository;
        this.sessionManipulator = sessionManipulator;
        this.contractRepository = contractRepository;
        this.notificationManipulator = notificationManipulator;
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

        Session session = sessionManipulator.getCurrentSession();

        Student student = studentDTO.fromDTO();
        hashAndSaltPassword(student);

        studentSessionRepository.save(new StudentSession(null, student, session));

        return Optional.of(studentRepository.save(student).toDTO());
    }

    @Transactional
    public List<OfferDTO> getAcceptedOffersByDepartment(Long departmentId, Long sessionId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        List<Offer> offers = offerRepository.findAllByDepartmentAndOfferStatus(department, OfferStatus.ACCEPTED);

        return sessionManipulator
                .removeOffersNotInSession(offers, sessionId)
                .stream()
                .map(Offer::toDTO)
                .toList();
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

        Cv cv = new Cv(null, fileName, bytes, student);
        Cv returnedCv = cvRepository.save(cv);
        notificationManipulator. makeNotificationCvToAllManagersByDepartment(returnedCv, Notification.NotificationCause.CREATED);
        return Optional.of(returnedCv.toDTO());
    }

    public Optional<ApplicationDTO> createApplication(ApplicationDTO applicationDTO){
        if(applicationDTO == null) throw new IllegalArgumentException("Application cannot be null");

        Long cvId = applicationDTO.getCv().getId();
        Long offerId = applicationDTO.getOffer().getId();
        Long studentId = applicationDTO.getCv().getStudent().getId();

        List<Application> alreadyApplied = applicationRepository.findApplicationsByCvStudentIdAndOfferId(studentId, offerId);

        if (!alreadyApplied.isEmpty()) throw new IllegalArgumentException("Student already applied to this offer");


        Cv cv = cvRepository.findById(cvId).orElseThrow(() -> new NoSuchElementException("Cv not found"));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        Application application = new Application(null, cv, offer);

        Application returnedApplication = applicationRepository.save(application);

        notificationManipulator.makeNotificationApplicationToOfferEmployer(returnedApplication, Notification.NotificationCause.CREATED);

        return Optional.of(returnedApplication.toDTO());
    }

    public List<CvDTO> getCvsByStudent(Long studentId) {
        if (studentId == null) throw new IllegalArgumentException("Student ID cannot be null");

        List<Cv> cvs = cvRepository.findAllByStudentId(studentId);

        return cvs.stream().map(Cv::toDTO).toList();
    }

    public List<ApplicationDTO> getApplicationsByStudentIdAndSessionId(Long studentId, Long sessionId) {
        if (studentId == null) throw new IllegalArgumentException("Student ID cannot be null");

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student not found"));
        List<Application> applications = applicationRepository.findApplicationsByCv_Student(student);

        return sessionManipulator.removeApplicationsNotInSession(applications, sessionId).stream().map(Application::toDTO).toList();
    }

    public Optional<ContractDTO> signContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new NoSuchElementException("Contract not found"));

        Student student = studentRepository.findByCv(contract.getApplication().getCv()).orElseThrow(() -> new NoSuchElementException("Student not found"));

        contract.setStudentSignature(new Signature<Student>(student, LocalDate.now()));

        return Optional.of(contractRepository.save(contract).toDTO());
    }
}
