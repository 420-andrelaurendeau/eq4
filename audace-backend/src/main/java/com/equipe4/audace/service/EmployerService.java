package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployerService extends GenericUserService<Employer> {
    private final EmployerRepository employerRepository;
    private OfferRepository offerRepository;
    private DepartmentRepository departmentRepository;

    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        if (employerDTO == null) throw new IllegalArgumentException("Employer cannot be null");

        Optional<Employer> employerOptional = employerRepository.findByEmail(employerDTO.getEmail());

        if(employerOptional.isPresent()) throw new IllegalArgumentException("Email already in use");

        return Optional.of(employerRepository.save(employerDTO.fromDTO()).toDTO());
    }
    public Optional<EmployerDTO> findEmployerById(Long employerId){
        return employerRepository.findById(employerId).map(Employer::toDTO);
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(Employer::toDTO).toList();
    }

    public Optional<OfferDTO> createOffer(OfferDTO offerDTO){
        if(offerDTO == null) throw new IllegalArgumentException("Offer cannot be null");

        Offer offer = offerDTO.fromDTO();
        offer.setStatus(Offer.Status.PENDING);

        return Optional.of(offerRepository.save(offer).toDTO());
    }
    public List<OfferDTO> findAllOffersByEmployerId(Long employerId){
        Employer employer = employerRepository.findById(employerId).orElseThrow();
        return offerRepository.findAllByEmployer(employer).stream().map(Offer::toDTO).toList();
    }
    public Optional<OfferDTO> updateOffer(OfferDTO offerDTO){
        Offer offer = offerRepository.findById(offerDTO.getId()).orElseThrow();
        return Optional.of(offerRepository.save(offer).toDTO());
    }
    public void deleteOffer(Long offerId){
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offerRepository.delete(offer);
    }



}