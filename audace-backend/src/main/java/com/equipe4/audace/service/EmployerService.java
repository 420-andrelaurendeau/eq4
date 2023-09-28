package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService extends UserService {
    private final EmployerRepository employerRepository;

    public EmployerService(
            EmployerRepository employerRepository,
            OfferRepository offerRepository,
            DepartmentRepository departmentRepository){
        super(offerRepository, departmentRepository);
        this.employerRepository = employerRepository;
    }

    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        return Optional.of(new EmployerDTO(employerRepository.save(employerDTO.fromDTO())));
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(EmployerDTO::new).toList();
    }
}