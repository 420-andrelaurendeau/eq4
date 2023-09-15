package com.equipe4.audace.model;


import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employer extends User{
    private String organisation;
    private String position;
    private String extension;

    @Builder(builderMethodName = "employerBuilder")
    public Employer(String firstName, String lastName, String email, String password, String address, String phone, String organisation, String position, String extension) {
        super(firstName, lastName, email, password, address, phone);
        this.organisation = organisation;
        this.position = position;
        this.extension = extension;
    }
}