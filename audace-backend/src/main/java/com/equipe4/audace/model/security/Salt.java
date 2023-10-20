package com.equipe4.audace.model.security;

import com.equipe4.audace.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Salt {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User user;
    private String value;
}
