package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.repository.EmployerRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployerService {
    public EmployerRepository employerRepository;

    public Optional<EmployerDTO> saveEmployer(EmployerDTO employerDTO){
        return Optional.of(new EmployerDTO(employerRepository.save(employerDTO.fromDTO())));
    }

    public List<EmployerDTO> findAllEmployers(){
        List<EmployerDTO> dtos = new ArrayList<>();
        for(Employer employer: employerRepository.findAll()) dtos.add(new EmployerDTO(employer));

        return dtos;
    }
}
