package com.equipe4.audace.model.contract;

import com.equipe4.audace.dto.contract.SignatureDTO;
import com.equipe4.audace.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Signature <T extends User> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signature_gen")
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private T signatory;
    private LocalDate signatureDate;

    @ManyToOne
    private Contract contract;

    public SignatureDTO toDTO(){
        return new SignatureDTO(
            id,
            signatory.getId(),
            signatureDate
        );
    }
}