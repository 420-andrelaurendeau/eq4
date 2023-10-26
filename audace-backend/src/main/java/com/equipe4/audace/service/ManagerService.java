package com.equipe4.audace.service;

import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.cv.Cv.CvStatus;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.OfferStatus;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
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
    private final ApplicationRepository applicationRepository;

    public ManagerService(
            SaltRepository saltRepository,
            ManagerRepository managerRepository,
            OfferRepository offerRepository,
            DepartmentRepository departmentRepository,
            CvRepository cvRepository,
            ApplicationRepository applicationRepository
    ) {
        super(saltRepository);
        this.managerRepository = managerRepository;
        this.offerRepository = offerRepository;
        this.departmentRepository = departmentRepository;
        this.cvRepository = cvRepository;
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
        offer.setOfferStatus(offerStatus);
        return Optional.of(offerRepository.save(offer).toDTO());
    }

    @Transactional
    public List<OfferDTO> getOffersByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        List<Offer> offers = offerRepository.findAllByDepartment(department);

        return offers.stream().map(Offer::toDTO).toList();
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

    public List<CvDTO> getCvsByDepartment(Long departmentId) {
        return cvRepository
                .findAllByStudentDepartmentId(departmentId)
                .stream().map(Cv::toDTO).toList();
    }

    public List<ApplicationDTO> getAcceptedApplicationsByDepartment(Long departmentId) {
        return applicationRepository
                .findApplicationsByApplicationStatusAndCv_StudentDepartmentId(
                        Application.ApplicationStatus.ACCEPTED, departmentId)
                .stream().map(Application::toDTO).toList();
    }
}
