package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;
    private final DepartmentRepository departmentRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository, DepartmentRepository departmentRepository, OfferRepository offerRepository) {
        this.employerRepository = employerRepository;
        this.departmentRepository = departmentRepository;
        this.offerRepository = offerRepository;
    }

    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        return Optional.of(new EmployerDTO(employerRepository.save(employerDTO.fromDTO())));
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(EmployerDTO::new).toList();
    }

    public Employer findEmployerById(Long employerId){
        return employerRepository.findById(employerId).orElseThrow(() -> new NoSuchElementException("Employer not found"));
    }

    /** Offers */

    @Transactional
    public Optional<OfferDTO> createOffer(OfferDTO offerDTO) {
        if (offerDTO == null) return Optional.empty();

        if (offerRepository.existsById(offerDTO.getId())) return Optional.empty();
        if (!employerRepository.existsById(offerDTO.getEmployerId())) return Optional.empty();
        if (!departmentRepository.existsById(offerDTO.getDepartment().getId())) return Optional.empty();

        Employer employer = findEmployerById(offerDTO.getEmployerId());
        Offer offer = offerDTO.fromDto(employer);
        if (!offer.isDateValid() && !offer.isOfferValid()) return Optional.empty();

        return Optional.of(offerRepository.save(offerDTO.fromDto(employer)).toDto());
    }
}