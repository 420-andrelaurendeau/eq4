package com.equipe4.audace.dto.contract;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class SignatureDTO {
    private Long id;

    private LocalDate signatureDate;

}