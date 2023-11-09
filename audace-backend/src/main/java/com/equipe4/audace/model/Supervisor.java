package com.equipe4.audace.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Supervisor {
    protected String firstName;
    protected String lastName;
    private String position;
    protected String email;
    protected String phone;
    private String extension;
}
