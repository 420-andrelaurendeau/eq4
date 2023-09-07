package com.equipe4.audace.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="SEQUENCE_USER")
    protected int id;
}
