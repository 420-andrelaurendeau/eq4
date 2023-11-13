package com.equipe4.audace.service;

import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.application.StudentsByInternshipFoundStatus;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.cv.Cv.CvStatus;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.OfferStatus;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.utils.NotificationManipulator;
import com.equipe4.audace.utils.SessionManipulator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ManagerService extends GenericUserService<Manager> {
    private final ManagerRepository managerRepository;
    private final OfferRepository offerRepository;
    private final DepartmentRepository departmentRepository;
    private final CvRepository cvRepository;
    private final ApplicationRepository applicationRepository;
    private final SessionManipulator sessionManipulator;
    private final ContractRepository contractRepository;
    private final StudentRepository studentRepository;
    private final NotificationManipulator notificationManipulator;

    public ManagerService(SaltRepository saltRepository, ManagerRepository managerRepository, OfferRepository offerRepository,
                          DepartmentRepository departmentRepository, CvRepository cvRepository, ContractRepository contractRepository,
                          SessionManipulator sessionManipulator, ApplicationRepository applicationRepository, NotificationManipulator notificationManipulator,
                          StudentRepository studentRepository) {
        super(saltRepository);
        this.managerRepository = managerRepository;
        this.offerRepository = offerRepository;
        this.departmentRepository = departmentRepository;
        this.cvRepository = cvRepository;
        this.contractRepository = contractRepository;
        this.sessionManipulator = sessionManipulator;
        this.applicationRepository = applicationRepository;
        this.notificationManipulator = notificationManipulator;
        this.studentRepository = studentRepository;
    }

    public Optional<ManagerDTO> getManagerById(Long id) {
        return managerRepository.findById(id).map(Manager::toDTO);
    }

    public DepartmentDTO getDepartmentByManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new NoSuchElementException("Manager not found with ID: " + managerId));

        return manager.getDepartment().toDTO();
    }

    @Transactional
    public Optional<CvDTO> acceptCv(Long managerId, Long cvId) {
        return setCvStatus(managerId, cvId, Cv.CvStatus.ACCEPTED);
    }

    @Transactional
    public Optional<CvDTO> refuseCv(Long managerId, Long cvId) {
        return setCvStatus(managerId, cvId, CvStatus.REFUSED);
    }

    private Optional<CvDTO> setCvStatus(Long managerId, Long cvId, CvStatus cvStatus) {
        Cv cv = cvRepository.findById(cvId).orElseThrow();
        Department studentDepartment = cv.getStudent().getDepartment();
        Department managerDepartment = managerRepository.findById(managerId).orElseThrow().getDepartment();

        if (!studentDepartment.equals(managerDepartment)) {
            throw new IllegalArgumentException("The manager isn't in the right department");
        }

        cv.setCvStatus(cvStatus);
        notificationManipulator.makeNotificationCvToCvStudent(cv, Notification.NotificationCause.UPDATED);
        return Optional.of(cvRepository.save(cv).toDTO());
    }

    public List<CvDTO> getCvsByDepartment(Long departmentId, Long sessionId) {
        List<Cv> cvs = cvRepository.findAllByStudentDepartmentId(departmentId);

        return sessionManipulator
                .removeCvsBelongingToStudentNotInSession(cvs, sessionId)
                .stream()
                .map(Cv::toDTO)
                .toList();
    }

    private Optional<OfferDTO> setOfferStatus(Long managerId, Long offerId, OfferStatus offerStatus) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        Department managerDepartment = managerRepository.findById(managerId).orElseThrow().getDepartment();
        Department offerDepartment = offer.getDepartment();

        if (!managerDepartment.equals(offerDepartment)) throw new IllegalArgumentException("The manager isn't in the right department");


        if (!sessionManipulator.isOfferInCurrentSession(offer)) throw new NoSuchElementException("Offer not found");

        offer.setOfferStatus(offerStatus);
        notificationManipulator.makeNotificationOfferToOfferEmployer(offer, Notification.NotificationCause.UPDATED);
        return Optional.of(offerRepository.save(offer).toDTO());
    }
    @Transactional
    public Optional<OfferDTO> acceptOffer(Long managerId, Long offerId) {
        notificationManipulator.makeNotificationOfferToAllStudents(
                offerRepository.findById(offerId).orElseThrow(),
                Notification.NotificationCause.CREATED
        );
        return setOfferStatus(managerId, offerId, OfferStatus.ACCEPTED);
    }

    @Transactional
    public Optional<OfferDTO> refuseOffer(Long managerId, Long offerId) {
        return setOfferStatus(managerId, offerId, OfferStatus.REFUSED);
    }

    @Transactional
    public List<OfferDTO> getOffersByDepartmentIdAndSessionId(Long departmentId, Long sessionId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new NoSuchElementException("Department not found"));
        List<Offer> offers = offerRepository.findAllByDepartment(department);

        return sessionManipulator.removeOffersNotInSession(offers, sessionId).stream().map(Offer::toDTO).toList();
    }

    public Optional<ApplicationDTO> getApplicationById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new NoSuchElementException("Application not found"));
        return Optional.of(application.toDTO());
    }

    public List<ApplicationDTO> getAcceptedApplicationsByManagerIdAndDepartmentId(Long managerId, Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new NoSuchElementException("Department not found"));
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new NoSuchElementException("Manager not found"));

        Department managerDepartment = manager.getDepartment();
        if (!managerDepartment.getCode().equals(department.getCode())) throw new IllegalArgumentException("The manager isn't in the right department");

        return applicationRepository.findAllByApplicationStatusAndAndOffer_Department(Application.ApplicationStatus.ACCEPTED, department).stream().map(Application::toDTO).toList();
    }

    public Optional<ContractDTO> createContract(ContractDTO contractDTO){
        if(contractDTO == null) throw new IllegalArgumentException("Contract cannot be null");

        Contract contract = contractDTO.fromDTO();

        return Optional.of(contractRepository.save(contract).toDTO());
    }
    public Optional<ContractDTO> findContractById(Long contractId){
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new NoSuchElementException("Contract not found"));
        return Optional.of(contract.toDTO());
    }

    public Optional<ContractDTO> getContractByApplicationId(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new NoSuchElementException("Application not found"));

        return contractRepository.findByApplication(application).map(Contract::toDTO);
    }

    public List<ContractDTO> getContractsByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new NoSuchElementException("Department not found"));

        return contractRepository.findAllByApplication_Offer_Department(department).stream().map(Contract::toDTO).toList();
    }

    @Transactional
    public StudentsByInternshipFoundStatus getStudentsByInternshipFoundStatus(Long departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));

        List<StudentDTO> studentsWithInternship = getStudentsWithInternship(departmentId);
        List<StudentDTO> studentsWithHigherPriorityStatuses = new ArrayList<>(studentsWithInternship);

        List<StudentDTO> studentsWithAcceptedResponse = getStudentsWithAcceptedResponse(
                departmentId,
                studentsWithHigherPriorityStatuses
        );
        List<StudentDTO> studentsWithPendingResponse = getStudentsWithPendingResponse(
                departmentId,
                studentsWithHigherPriorityStatuses
        );
        List<StudentDTO> studentsWithRefusedResponse = getStudentsWithRefusedResponse(
                departmentId,
                studentsWithHigherPriorityStatuses
        );

        List<StudentDTO> studentsWithoutApplications = getStudentsWithoutApplications(departmentId);

        return new StudentsByInternshipFoundStatus(
                studentsWithInternship,
                studentsWithAcceptedResponse,
                studentsWithPendingResponse,
                studentsWithRefusedResponse,
                studentsWithoutApplications
        );
    }

    private List<StudentDTO> getStudentsWithInternship(Long departmentId) {
        return contractRepository
                .findAllByApplicationCvStudentDepartmentId(departmentId)
                .stream()
                .map(Contract::getApplication)
                .map(Application::getCv)
                .map(Cv::getStudent)
                .map(Student::toDTO)
                .toList();
    }

    private List<StudentDTO> getStudentsWithAcceptedResponse(
            Long departmentId,
            List<StudentDTO> studentsWithHigherPriorityStatuses
    ) {
        return getStudentsWithApplicationResponse(
                departmentId,
                studentsWithHigherPriorityStatuses,
                Application.ApplicationStatus.ACCEPTED
        );
    }

    private List<StudentDTO> getStudentsWithPendingResponse(
            Long departmentId,
            List<StudentDTO> studentsWithHigherPriorityStatuses
    ) {
        return getStudentsWithApplicationResponse(
                departmentId,
                studentsWithHigherPriorityStatuses,
                Application.ApplicationStatus.PENDING
        );
    }

    private List<StudentDTO> getStudentsWithRefusedResponse(
            Long departmentId,
            List<StudentDTO> studentsWithHigherPriorityStatuses
    ) {
        return getStudentsWithApplicationResponse(
                departmentId,
                studentsWithHigherPriorityStatuses,
                Application.ApplicationStatus.REFUSED
        );
    }

    private List<StudentDTO> getStudentsWithApplicationResponse(
            Long departmentId,
            List<StudentDTO> studentsWithHigherPriorityStatuses,
            Application.ApplicationStatus applicationStatus
    ) {
        List<StudentDTO> studentsWithApplicationResponse = applicationRepository
                .findAllByCvStudentDepartmentId(departmentId)
                .stream()
                .filter(application -> application.getApplicationStatus() == applicationStatus)
                .map(Application::getCv)
                .map(Cv::getStudent)
                .map(Student::toDTO)
                .filter(dto -> !studentsWithHigherPriorityStatuses.contains(dto))
                .toList();

        studentsWithHigherPriorityStatuses.addAll(studentsWithApplicationResponse);

        return studentsWithApplicationResponse;
    }

    private List<StudentDTO> getStudentsWithoutApplications(Long departmentId) {
        return studentRepository
                .findAllWithoutApplicationsByDepartmentId(departmentId)
                .stream()
                .map(Student::toDTO)
                .toList();
    }
}
