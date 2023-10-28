package com.equipe4.audace.model.session;

import com.equipe4.audace.dto.session.SessionDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Session {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    public SessionDTO toDTO() {
        return new SessionDTO(
                id,
                startDate,
                endDate
        );
    }
}
