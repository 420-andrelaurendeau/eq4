package com.equipe4.audace.dto;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployerDTO {
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String organisation;
    @Column
    private String position;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String extension;

    private List<OfferDTO> offers;

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
        this.offers = new ArrayList<>();
    }

    @Builder(builderMethodName = "employerDTOBuilder")
    public EmployerDTO(Long id,
                       String firstName,
                       String lastName,
                       String email,
                       String password,
                       String organisation,
                       String position,
                       String address,
                       String phone,
                       String extension,
                       List<OfferDTO> offers) {
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
        this.offers = offers;
    }

    public Employer fromDTO(){
        Employer employer = Employer.employerBuilder()
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
        employer.setOffers(offers.stream().map(offerDTO -> offerDTO.fromDto(employer)).toList());

        return employer;
    }
}