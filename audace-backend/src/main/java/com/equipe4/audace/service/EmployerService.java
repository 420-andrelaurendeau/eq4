package com.equipe4.audace.service;

import com.equipe4.audace.dto.ApplicationDTO;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class EmployerService extends GenericUserService<Employer> {
    private final EmployerRepository employerRepository;
    private OfferRepository offerRepository;
    private ApplicationRepository applicationRepository;

    public EmployerService(SaltRepository saltRepository, EmployerRepository employerRepository, OfferRepository offerRepository, ApplicationRepository applicationRepository) {
        super(saltRepository);
        this.employerRepository = employerRepository;
        this.offerRepository = offerRepository;
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        if (employerDTO == null) throw new IllegalArgumentException("Employer cannot be null");

        Optional<Employer> employerOptional = employerRepository.findByEmail(employerDTO.getEmail());

        if(employerOptional.isPresent()) throw new IllegalArgumentException("Email already in use");

        Employer employer = employerDTO.fromDTO();
        hashAndSaltPassword(employer);
        return Optional.of(employerRepository.save(employer).toDTO());
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
        offer.setOfferStatus(Offer.OfferStatus.PENDING);

        return Optional.of(offerRepository.save(offer).toDTO());
    }

    public List<OfferDTO> findAllOffersByEmployerId(Long employerId){
        Employer employer = employerRepository.findById(employerId).orElseThrow(() -> new NoSuchElementException("Employer not found"));
        return offerRepository.findAllByEmployer(employer).stream().map(Offer::toDTO).toList();
    }

    public Optional<OfferDTO> updateOffer(OfferDTO offerDTO){
        Offer offer = offerRepository.findById(offerDTO.getId()).orElseThrow(() -> new NoSuchElementException("Offer not found"));
        return Optional.of(offerRepository.save(offer).toDTO());
    }

    public void deleteOffer(Long offerId){
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));
        offerRepository.delete(offer);
    }

    public List<ApplicationDTO> findAllApplicationsByOfferId(Long offerId){
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));
        return applicationRepository.findAllByOffer(offer).stream().map(Application::toDTO).toList();
    }

    public Map<OfferDTO, List<ApplicationDTO>> findAllApplicationsByEmployerId(Long employerId){
        Map<OfferDTO, List<ApplicationDTO>> map = new HashMap<>();

        for (OfferDTO offerDTO: findAllOffersByEmployerId(employerId)){
            map.put(offerDTO, findAllApplicationsByOfferId(offerDTO.getId()));
        }
        return map;
    }
}