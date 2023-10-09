package com.equipe4.audace.dto;

import com.equipe4.audace.model.Employer;
import lombok.*;

@Data
@NoArgsConstructor
public class EmployerDTO extends UserDTO{
    private String organisation;
    private String position;
    private String extension;


    @Builder(builderMethodName = "employerDTOBuilder")
    public EmployerDTO(Long id, String firstName, String lastName, String address, String phone, String email, String password, String organisation, String position, String extension) {
        super(id, firstName, lastName, address, phone, email, password);
        this.organisation = organisation;
        this.position = position;
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