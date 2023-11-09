package com.equipe4.audace.model.contract;

import com.equipe4.audace.model.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Signature<T extends User> {
    LocalDate date;

    @OneToOne
    private T signatory;

    public Signature(T signatory) {
        this.date = LocalDate.now();
        this.signatory = signatory;
    }
}