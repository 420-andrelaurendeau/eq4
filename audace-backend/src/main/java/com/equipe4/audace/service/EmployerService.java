package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;
    private OfferRepository offerRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository, OfferRepository offerRepository, DepartmentRepository departmentRepository) {
        this.employerRepository = employerRepository;
        this.offerRepository = offerRepository;
        this.departmentRepository = departmentRepository;
    }

    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        if (employerDTO == null) throw new IllegalArgumentException("Employer cannot be null");

        Optional<Employer> employerOptional = employerRepository.findByEmail(employerDTO.getEmail());

        if (employerOptional.isPresent()) throw new IllegalArgumentException("Email already in use");

        return Optional.of(new EmployerDTO(employerRepository.save(employerDTO.fromDTO())));
    }
    public Optional<EmployerDTO> findEmployerById(Long employerId){
        Employer employer = employerRepository.findById(employerId).get();
        return Optional.of(new EmployerDTO(employer));
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(EmployerDTO::new).toList();
    }

    public Optional<OfferDTO> createOffer(OfferDTO offerDTO){
        if(offerDTO == null) throw new IllegalArgumentException("Offer cannot be null");

        Employer employer = employerRepository.findById(offerDTO.getEmployerId()).get();
        Department department = departmentRepository.findByCode(offerDTO.getDepartmentCode()).get();

        Offer offer = offerDTO.fromDto();
        offer.setEmployer(employer);
        offer.setDepartment(department);

        return Optional.of(new OfferDTO(offerRepository.save(offer)));
    }
    public List<OfferDTO> findAllOffersByEmployerId(Long employerId){
        Employer employer = employerRepository.findById(employerId).get();
        return offerRepository.findAllByEmployer(employer).stream().map(OfferDTO::new).toList();
    }
    public Optional<OfferDTO> updateOffer(OfferDTO offerDTO){
        Offer offer = offerRepository.findById(offerDTO.getId()).get();
        return Optional.of(new OfferDTO(offerRepository.save(offer)));
    }
    public void deleteOffer(Long offerId){
        Offer offer = offerRepository.findById(offerId).get();
        offerRepository.deleteById(offerId);
    }



}