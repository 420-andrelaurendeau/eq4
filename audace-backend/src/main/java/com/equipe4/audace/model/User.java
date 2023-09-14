package com.equipe4.audace.model;

import com.equipe4.audace.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUDACE_USER")
@Data
public abstract class User {
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="SEQUENCE_USER")
    protected Long id;
    @Column
    protected String email;
    @Column
    protected String password;

    public abstract UserDTO toDTO();
}
