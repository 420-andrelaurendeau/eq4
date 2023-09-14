package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@AllArgsConstructor

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        Employer employer = employerDTO.getEmployerFromDTO();
        return Optional.of(new EmployerDTO(employerRepository.save(employer)));
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(EmployerDTO::new).toList();
    }
}
