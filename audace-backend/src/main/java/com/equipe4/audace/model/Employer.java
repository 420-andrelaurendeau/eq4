package com.equipe4.audace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Entity
public class Employer extends User {
    @Column
    private String organisation;
    @Column
    private String position;
    @Column
    private String extension;

    @Builder(builderMethodName = "employerBuilder")
    public Employer(String firstName, String lastName, String email, String password, String address, String phone, String organisation, String position, String extension) {
        super(firstName, lastName, email, password, address, phone);
        this.organisation = organisation;
        this.position = position;
        this.extension = extension;
    }
}
