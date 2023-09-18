package com.equipe4.audace.model;

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
@Entity
public class Employer extends User {
    @Column
    private String organisation;
    @Column
    private String position;
    @Column
    private String extension;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<Offer> offers;

    @Builder(builderMethodName = "employerBuilder")
    public Employer(String firstName,
                    String lastName,
                    String email,
                    String password,
                    String address,
                    String phone,
                    String organisation,
                    String position,
                    String extension) {
        super(firstName, lastName, email, password, address, phone);
        this.organisation = organisation;
        this.position = position;
        this.extension = extension;
        this.offers = new ArrayList<>();
    }
}