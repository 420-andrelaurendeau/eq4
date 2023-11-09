package com.equipe4.audace.model.contract;

import com.equipe4.audace.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Signature<T extends User> {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private T signatory;

    private LocalDate signatureDate;

    public Signature(T signatory) {
        this.signatory = signatory;
        this.signatureDate = LocalDate.now();
    }
}