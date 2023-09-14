package com.equipe4.audace.dto;

import com.equipe4.audace.model.Employer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String organization;
    private String position;
    private String address;
    private String phone;
    private String extension;

    public EmployerDTO(Employer employer) {
        this.id = employer.getId();
        this.firstName = employer.getFirstName();
        this.lastName = employer.getLastName();
        this.email = employer.getEmail();
        this.password = employer.getPassword();
        this.organization = employer.getOrganization();
        this.position = employer.getPosition();
        this.address = employer.getAddress();
        this.phone = employer.getPhone();
        this.extension = employer.getExtension();
    }

    public Employer getEmployerFromDTO() {
        return Employer.builder().employerDTO(this).build();
    }
}
