package com.equipe4.audace.model.contract;

import com.equipe4.audace.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Signature<T extends User> {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private T signatory;

    @Column(insertable=false, updatable=false)
    private LocalDate signatureDate;

}