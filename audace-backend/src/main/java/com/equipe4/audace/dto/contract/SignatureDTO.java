package com.equipe4.audace.dto.contract;

import com.equipe4.audace.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SignatureDTO {
    private Long id;
    private Long signatoryId;
    private LocalDate signatureDate;

}