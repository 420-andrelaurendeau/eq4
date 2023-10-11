package com.equipe4.audace.model;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.model.offer.Offer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
public class Employer extends User {

    private String organisation;
    private String position;
    private String extension;

    @ToString.Exclude
    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();

    public Employer(
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
        super(id, firstName, lastName, email, password, address, phone);
        this.organisation = organisation;
        this.position = position;
        this.extension = extension;
    }

    @Override
    public EmployerDTO toDTO() {
        return EmployerDTO.employerDTOBuilder()
                .id(id)
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
