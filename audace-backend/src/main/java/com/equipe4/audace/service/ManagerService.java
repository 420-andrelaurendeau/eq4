package com.equipe4.audace.service;

import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.cv.Cv.CvStatus;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.OfferStatus;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManagerService extends GenericUserService<Manager> {
    private final ManagerRepository managerRepository;
    private final OfferRepository offerRepository;
    private final DepartmentRepository departmentRepository;
    private final CvRepository cvRepository;
    private final ContractRepository contractRepository;

    public ManagerService(SaltRepository saltRepository, ManagerRepository managerRepository, OfferRepository offerRepository,
                          DepartmentRepository departmentRepository, CvRepository cvRepository, ContractRepository contractRepository,
                          ApplicationRepository applicationRepository) {
        super(saltRepository, applicationRepository);
        this.managerRepository = managerRepository;
        this.offerRepository = offerRepository;
        this.departmentRepository = departmentRepository;
        this.cvRepository = cvRepository;
        this.contractRepository = contractRepository;
    }

    @Transactional
    public Optional<OfferDTO> acceptOffer(Long offerId) {
        return setOfferStatus(offerId, OfferStatus.ACCEPTED);
    }

    @Transactional
    public Optional<OfferDTO> refuseOffer(Long offerId) {
        return setOfferStatus(offerId, OfferStatus.REFUSED);
    }

    private Optional<OfferDTO> setOfferStatus(Long offerId, OfferStatus offerStatus) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
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
    public Optional<CvDTO> acceptCv(Long cvId) {
        return setCvStatus(cvId, Cv.CvStatus.ACCEPTED);
    }

    @Transactional
    public Optional<CvDTO> refuseCv(Long cvId) {
        return setCvStatus(cvId, CvStatus.REFUSED);
    }

    private Optional<CvDTO> setCvStatus(Long cvId, CvStatus cvStatus) {
        Cv cv = cvRepository.findById(cvId).orElseThrow();
        cv.setCvStatus(cvStatus);
        return Optional.of(cvRepository.save(cv).toDTO());
    }

    public List<CvDTO> getCvsByDepartment(Long departmentId) {
        return cvRepository
                .findAllByStudentDepartmentId(departmentId)
                .stream().map(Cv::toDTO).toList();
    }

    public Optional<ContractDTO> createContract(ContractDTO contractDTO){
        if(contractDTO == null) throw new IllegalArgumentException("Contract cannot be null");

        return Optional.of(contractRepository.save(contractDTO.fromDTO()).toDTO());
    }
}
