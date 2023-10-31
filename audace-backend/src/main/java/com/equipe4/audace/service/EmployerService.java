package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployerService extends GenericUserService<Employer> {
    private final EmployerRepository employerRepository;
    private final OfferRepository offerRepository;

    private final DepartmentRepository departmentRepository;

    public EmployerService(
            SaltRepository saltRepository,
            EmployerRepository employerRepository,
            OfferRepository offerRepository,
            DepartmentRepository departmentRepository
    ) {
        super(saltRepository);
        this.employerRepository = employerRepository;
        this.offerRepository = offerRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        if (employerDTO == null) throw new IllegalArgumentException("Employer cannot be null");

        Optional<Employer> employerOptional = employerRepository.findByEmail(employerDTO.getEmail());

        if(employerOptional.isPresent()) throw new IllegalArgumentException("Email already in use");

        Employer newEmployer = employerDTO.fromDTO();
        hashAndSaltPassword(newEmployer);
        return Optional.of(employerRepository.save(newEmployer).toDTO());
    }

    public Optional<EmployerDTO> findEmployerById(Long employerId){
        return employerRepository.findById(employerId).map(Employer::toDTO);
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(Employer::toDTO).toList();
    }

    public Optional<OfferDTO> createOffer(OfferDTO offerDTO){
        if(offerDTO == null) throw new IllegalArgumentException("Offer cannot be null");

        Employer employer = employerRepository.findById(offerDTO.getEmployerId()).orElseThrow();
        Department department = departmentRepository.findByCode(offerDTO.getDepartmentCode()).orElseThrow();

        Offer offer = offerDTO.fromDTO();
        offer.setEmployer(employer);
        offer.setDepartment(department);

        return Optional.of(offerRepository.save(offer).toDTO());
    }

    public Optional<OfferDTO> findOfferById(Long offerId){
        return offerRepository.findById(offerId).map(Offer::toDTO);
    }

    public List<DepartmentDTO> findAllDepartments(){
        return departmentRepository.findAll().stream().map(Department::toDTO).toList();
    }

    public List<OfferDTO> findAllOffersByEmployerId(Long employerId){
        Employer employer = employerRepository.findById(employerId).orElseThrow();
        return offerRepository.findAllByEmployer(employer).stream().map(Offer::toDTO).toList();
    }
    public Optional<OfferDTO> updateOffer(OfferDTO offerDTO) {
        Offer offer = offerRepository.findById(offerDTO.getId()).orElseThrow(() -> new NoSuchElementException("Offer with ID " + offerDTO.getId() + " not found."));

        offer.setTitle(offerDTO.getTitle());
        offer.setDescription(offerDTO.getDescription());
        offer.setInternshipStartDate(offerDTO.getInternshipStartDate());
        offer.setInternshipEndDate(offerDTO.getInternshipEndDate());
        offer.setOfferEndDate(offerDTO.getOfferEndDate());
        offer.setAvailablePlaces(offerDTO.getAvailablePlaces());
        offer.setDepartment(departmentRepository.findByCode(offerDTO.getDepartmentCode()).orElseThrow());

        return Optional.of(offerRepository.save(offer).toDTO());
    }



    public void deleteOffer(Long offerId){
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offerRepository.delete(offer);
    }
}