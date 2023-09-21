package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.repository.EmployerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
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

    @Transactional
    public void createOffer(OfferDTO offerDTO) {
        Employer employer = findEmployerById(offerDTO.getEmployerId());
        employer.addOffer(offerDTO.fromDto(employer));
        employerRepository.save(employer);
    }
}