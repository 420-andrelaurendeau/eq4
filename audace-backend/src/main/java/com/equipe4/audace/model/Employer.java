package com.equipe4.audace.model;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.offer.Offer;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<Contract> contracts = new ArrayList<>();

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
        return new EmployerDTO(
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
