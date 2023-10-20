package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

        return Optional.of(new EmployerDTO(employerRepository.save(employerDTO.fromDTO())));
    }
    public Optional<EmployerDTO> findEmployerById(Long employerId){
        return employerRepository.findById(employerId).map(EmployerDTO::new);
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(EmployerDTO::new).toList();
    }

    public Optional<OfferDTO> createOffer(OfferDTO offerDTO){
        if(offerDTO == null) throw new IllegalArgumentException("Offer cannot be null");

        Employer employer = employerRepository.findById(offerDTO.getEmployerId()).orElseThrow();
        Department department = departmentRepository.findByCode(offerDTO.getDepartmentCode()).orElseThrow();

        Offer offer = offerDTO.fromDto();
        offer.setEmployer(employer);
        offer.setDepartment(department);

        return Optional.of(new OfferDTO(offerRepository.save(offer)));
    }

    public Optional<OfferDTO> findOfferById(Long offerId){
        return offerRepository.findById(offerId).map(OfferDTO::new);
    }

    public List<DepartmentDTO> findAllDepartments(){
        return departmentRepository.findAll().stream().map(DepartmentDTO::new).toList();
    }
    public List<OfferDTO> findAllOffersByEmployerId(Long employerId){
        Employer employer = employerRepository.findById(employerId).orElseThrow();
        return offerRepository.findAllByEmployer(employer).stream().map(OfferDTO::new).toList();
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

        return Optional.of(new OfferDTO(offerRepository.save(offer)));
    }

    public void deleteOffer(Long offerId){
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offerRepository.deleteById(offerId);
    }



}