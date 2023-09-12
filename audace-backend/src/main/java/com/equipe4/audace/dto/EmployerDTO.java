package com.equipe4.audace.dto;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        this.organisation = employer.getOrganisation();
        this.position = employer.getPosition();
        this.address = employer.getAddress();
        this.phone = employer.getPhone();
        this.extension = employer.getExtension();
        this.offers = employer.getOffers().stream().map(OfferDTO::new).toList();
    }

    @Builder(builderMethodName = "employerDTOBuilder")
    public EmployerDTO(Long id, String firstName, String lastName, String organisation, String position, String address, String phone, String extension, List<OfferDTO> offers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
