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
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signature_gen")
    @SequenceGenerator(name = "signature_gen", sequenceName = "signature_sec", allocationSize = 1)
    @Column(name = "signature_id")
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private T signatory;

    private LocalDate signatureDate;

    public Signature(T signatory) {
        this.signatory = signatory;
        this.signatureDate = LocalDate.now();
    }
}