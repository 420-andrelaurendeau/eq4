package com.equipe4.audace.service;

import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.cv.Cv.CvStatus;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.OfferStatus;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.utils.SessionManipulator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ManagerService extends GenericUserService<Manager> {
    private final ManagerRepository managerRepository;
    private final OfferRepository offerRepository;
    private final DepartmentRepository departmentRepository;
    private final CvRepository cvRepository;
    private final ContractRepository contractRepository;
    private final ApplicationRepository applicationRepository;
    private final SessionManipulator sessionManipulator;

    public ManagerService(
            SaltRepository saltRepository,
            ManagerRepository managerRepository,
            OfferRepository offerRepository,
            DepartmentRepository departmentRepository,
            CvRepository cvRepository,
            ContractRepository contractRepository
            SessionManipulator sessionManipulator,
            ApplicationRepository applicationRepository
    ) {
        super(saltRepository);
        this.managerRepository = managerRepository;
        this.offerRepository = offerRepository;
        this.departmentRepository = departmentRepository;
        this.cvRepository = cvRepository;
        this.contractRepository = contractRepository;
        this.sessionManipulator = sessionManipulator;
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public Optional<OfferDTO> acceptOffer(Long managerId, Long offerId) {
        return setOfferStatus(managerId, offerId, OfferStatus.ACCEPTED);
    }

    @Transactional
    public Optional<OfferDTO> refuseOffer(Long managerId, Long offerId) {
        return setOfferStatus(managerId, offerId, OfferStatus.REFUSED);
    }

    private Optional<OfferDTO> setOfferStatus(Long managerId, Long offerId, OfferStatus offerStatus) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        Department managerDepartment = managerRepository.findById(managerId).orElseThrow().getDepartment();
        Department offerDepartment = offer.getDepartment();

        if (!managerDepartment.equals(offerDepartment)) {
            throw new IllegalArgumentException("The manager isn't in the right department");
        }

        if (!sessionManipulator.isOfferInCurrentSession(offer)) {
            throw new NoSuchElementException("Offer not found");
        }

        offer.setOfferStatus(offerStatus);
        return Optional.of(offerRepository.save(offer).toDTO());
    }

    @Transactional
    public List<OfferDTO> getOffersByDepartment(Long departmentId, Long sessionId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        List<Offer> offers = offerRepository.findAllByDepartment(department);

        return sessionManipulator
                .removeOffersNotInSession(offers, sessionId)
                .stream()
                .map(Offer::toDTO)
                .toList();
    }

    public Optional<ManagerDTO> getManagerById(Long id) {
        return managerRepository.findById(id).map(Manager::toDTO);
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

    public List<ApplicationDTO> getAcceptedApplicationsByDepartment(Long managerId, Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty()) {
            throw new NoSuchElementException("Department not found");
        }
        Department managerDepartment = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("Manager is not found"))
                .getDepartment();
        if (!managerDepartment.getId().equals(departmentId)) {
            throw new IllegalArgumentException("The manager isn't in the right department");
        }
        return applicationRepository
                .findApplicationsByApplicationStatusAndOfferDepartmentId(
                        Application.ApplicationStatus.ACCEPTED, departmentId)
                .stream().map(Application::toDTO).toList();
    }

    public DepartmentDTO getDepartmentByManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("Manager not found with ID: " + managerId));

        return manager.getDepartment().toDTO();
    }

    public Optional<ContractDTO> createContract(ContractDTO contractDTO){
        if(contractDTO == null) throw new IllegalArgumentException("Contract cannot be null");

        return Optional.of(contractRepository.save(contractDTO.fromDTO()).toDTO());
    }

    public Optional<ContractDTO> findContractById(Long contractId){
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new NoSuchElementException("Contract not found"));
        return Optional.of(contract.toDTO());
    }
}
