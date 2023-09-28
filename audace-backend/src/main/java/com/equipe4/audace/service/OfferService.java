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
    private final EmployerService employerService;
    private final DepartmentService departmentService;


    @Autowired
    public OfferService(OfferRepository offerRepository, EmployerService employerService, DepartmentService departmentService) {
        this.offerRepository = offerRepository;
        this.employerService = employerService;
        this.departmentService = departmentService;
    }

    public Optional<OfferDTO> createOffer(OfferDTO offerDTO){
        if(offerDTO == null) throw new IllegalArgumentException("Offer cannot be null");

        Employer employer = employerService.findEmployerById(offerDTO.getEmployerId());
        Department department = departmentService.findDepartmentByCode(offerDTO.getDepartmentCode());

        Offer offer = offerDTO.fromDTO();
        offer.setEmployer(employer);
        offer.setDepartment(department);

        return Optional.of(new OfferDTO(offerRepository.save(offer)));
    }
    public Optional<OfferDTO> updateOffer(OfferDTO offerDTO){
        Offer offer = findOfferById(offerDTO.getId());
        return Optional.of(new OfferDTO(offerRepository.save(offer)));
    }
    public void deleteOffer(Long offerId){
        if(findOfferById(offerId) != null) offerRepository.deleteById(offerId);
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
