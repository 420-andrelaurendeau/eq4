package com.equipe4.audace.dto;

import com.equipe4.audace.model.Employer;
import lombok.*;

@Data
@NoArgsConstructor
public class EmployerDTO extends UserDTO{
    private String organisation;
    private String position;
    private String extension;

    public EmployerDTO(
            Long id,
            String firstName,
            String lastName,
            String email,
            String password,
            String organisation,
            String position,
            String address,
            String phone,
            String extension
    ) {
        super(id, firstName, lastName, address, phone, email, password);
        this.organisation = organisation;
        this.position = position;
        this.extension = extension;
    }

    public Employer fromDTO(){
        return new Employer(
                id,
                firstName,
                lastName,
                email,
                password,
                organisation,
                position,
                address,
                phone,
                extension
        );
    }
}