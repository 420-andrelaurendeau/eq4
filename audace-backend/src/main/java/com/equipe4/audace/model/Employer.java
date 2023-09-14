package com.equipe4.audace.model;

import com.equipe4.audace.dto.EmployerDTO;
import jakarta.persistence.Entity;
import lombok.*;

@Getter

@Entity
public class Employer extends User {
    private String organization;
    private String position;
    private String extension;

    @Builder
    public Employer(EmployerDTO employerDTO) {
        super(employerDTO.getFirstName(), employerDTO.getLastName(), employerDTO.getEmail(), employerDTO.getPassword(), employerDTO.getAddress(), employerDTO.getPhone());
        this.organization = employerDTO.getOrganization();
        this.position = employerDTO.getPosition();
        this.extension = employerDTO.getExtension();
    }
}
