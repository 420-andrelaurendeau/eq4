package com.equipe4.audace.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SignatureDTO {
    private Long id;

    private LocalDate signatureDate;

}