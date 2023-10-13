package com.equipe4.audace.model;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.offer.Offer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
public class Employer extends User {
    private String organisation;
    private String position;
    private String extension;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();

    @Builder(builderMethodName = "employerBuilder")
    public Employer(Long id,
                    String firstName,
                    String lastName,
                    String email,
                    String password,
                    String organisation,
                    String position,
                    String address,
                    String phone,
                    String extension) {
        super(id, firstName, lastName, email, password, address, phone);
        this.organisation = organisation;
        this.position = position;
        this.extension = extension;
        this.offers = new ArrayList<>();
    }

    public EmployerDTO toDTO() {
        return new EmployerDTO(this.id, this.firstName, this.lastName, this.email, this.password, this.organisation, this.position, this.address, this.phone, this.extension);
    }
}
