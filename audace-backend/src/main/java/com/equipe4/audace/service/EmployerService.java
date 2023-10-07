package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService extends GenericUserService<Employer> {
    private final EmployerRepository employerRepository;

    public EmployerService(
            SaltRepository saltRepository,
            EmployerRepository employerRepository
    ) {
        super(saltRepository);
        this.employerRepository = employerRepository;
    }

    @Transactional
    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        Employer employer = employerDTO.fromDTO();
        hashAndSaltPassword(employer);

        return Optional.of(employerRepository.save(employer)).map(Employer::toDTO);
    }

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(EmployerDTO::new).toList();
    }

    public Optional<EmployerDTO> findEmployerById(Long id){
        return employerRepository.findById(id).map(EmployerDTO::new);
    }
}