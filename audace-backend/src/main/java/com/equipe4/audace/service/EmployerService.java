package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository, OfferRepository offerRepository) {
        this.employerRepository = employerRepository;
        this.offerRepository = offerRepository;
    }

    public Optional<EmployerDTO> saveEmployer(EmployerDTO employerDTO){
        return Optional.of(new EmployerDTO(employerRepository.save(employerDTO.fromDTO())));
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(EmployerDTO::new).toList();
    }

    public List<OfferDTO> getAllOfferByEmployerId(Long id) {
        return offerRepository.findAllByEmployerId(id).stream().map(OfferDTO::new).toList();
    }
}
