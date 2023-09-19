package com.equipe4.audace.dto;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EmployerDTO extends UserDTO {
    private String organisation;
    private String position;
    private String phone;
    private String extension;
    private List<OfferDTO> offers;

    public EmployerDTO(Employer employer) {
        super(employer.getId(), employer.getEmail(), employer.getPassword());
        this.organisation = employer.getOrganisation();
        this.position = employer.getPosition();
        this.extension = employer.getExtension();
        this.offers = employer.getOffers().stream().map(OfferDTO::new).toList();
    }

    @Builder(builderMethodName = "employerDTOBuilder")
    public EmployerDTO(Long id,
                       String organisation,
                       String position,
                       String phone,
                       String extension,
                       String email,
                       String password,
                       List<OfferDTO> offers) {
        super(id, email, password);
        this.organisation = organisation;
        this.position = position;
        this.phone = phone;
        this.extension = extension;
        this.offers = offers;
    }

    public Employer fromDTO(){
        Employer employer = Employer.employerBuilder()
                .email(email)
                .password(password)
                .organisation(organisation)
                .position(position)
                .extension(extension)
                .build();

        employer.setOffers(offers.stream().map(offerDTO -> offerDTO.fromDto(employer)).toList());

        return employer;
    }
}
