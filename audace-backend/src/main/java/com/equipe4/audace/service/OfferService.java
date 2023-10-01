package com.equipe4.audace.service;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final EmployerRepository employerRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployerService employerService;
    private final DepartmentService departmentService;




    @Autowired
    public OfferService(OfferRepository offerRepository, EmployerRepository employerRepository, DepartmentRepository departmentRepository, EmployerService employerService, DepartmentService departmentService) {
        this.offerRepository = offerRepository;
        this.employerRepository = employerRepository;
        this.departmentRepository = departmentRepository;
        this.employerService = employerService;
        this.departmentService = departmentService;
    }

    public Optional<OfferDTO> createOffer(OfferDTO offerDTO){
        if(offerDTO == null) throw new IllegalArgumentException("Offer cannot be null");

        if (offerRepository.existsById(offerDTO.getId())) throw new IllegalArgumentException("Offer already exists");
        if (!employerRepository.existsById(offerDTO.getEmployerId()))
            throw new IllegalArgumentException("Employer not found");
        if (!departmentRepository.existsByCode(offerDTO.getDepartmentCode()))
            throw new IllegalArgumentException("Department not found");

        Employer employer = employerService.findEmployerById(offerDTO.getEmployerId());
        Department department = departmentService.findDepartmentByCode(offerDTO.getDepartmentCode());

        Offer offer = offerDTO.fromDTO();
        offer.setEmployer(employer);
        offer.setDepartment(department);

        if(!offer.isOfferValid()) throw new IllegalArgumentException("Offer is not valid");
        if(!offer.isDateValid()) throw new IllegalArgumentException("Offer dates are not valid");

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
