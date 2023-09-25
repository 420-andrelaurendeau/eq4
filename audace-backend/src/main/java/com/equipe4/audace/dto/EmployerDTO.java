package com.equipe4.audace.dto;

import com.equipe4.audace.model.Employer;
import lombok.Builder;
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
    private String organisation;
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
        this.organisation = employer.getOrganisation();
        this.position = employer.getPosition();
        this.address = employer.getAddress();
        this.phone = employer.getPhone();
        this.extension = employer.getExtension();
    }

    @Builder(builderMethodName = "employerDTOBuilder")
    public EmployerDTO(Long id, String firstName, String lastName, String email, String password, String organisation, String position, String address, String phone, String extension) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.organisation = organisation;
        this.position = position;
        this.address = address;
        this.phone = phone;
        this.extension = extension;
    }

    public Employer fromDTO(){
        return Employer.employerBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .organisation(organisation)
                .position(position)
                .address(address)
                .phone(phone)
                .extension(extension)
        .build();
    }
}