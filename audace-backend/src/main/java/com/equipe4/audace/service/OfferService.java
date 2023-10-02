package com.equipe4.audace.service;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.offer.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private EmployerService employerService;
    private DepartmentService departmentService;


    @Autowired
    public OfferService(OfferRepository offerRepository, EmployerService employerService, DepartmentService departmentService) {
        this.offerRepository = offerRepository;
        this.employerService = employerService;
        this.departmentService = departmentService;
    }





    public Offer findOfferById(Long offerId){
        Optional<Offer> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isEmpty()) throw new IllegalArgumentException("Offer doesn't exists");
        return offerOptional.get();
    }
    public List<OfferDTO> findAllOffers(){
        return offerRepository.findAll().stream().map(OfferDTO::new).toList();
    }



}
