package com.equipe4.audace.model.offer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private Date internshipStartDate;
    private Date internshipEndDate;
    private Date offerEndDate;
}
