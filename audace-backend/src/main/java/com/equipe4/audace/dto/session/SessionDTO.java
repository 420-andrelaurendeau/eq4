package com.equipe4.audace.dto.session;

import com.equipe4.audace.model.session.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrentSession;

    public Session fromDTO() {
        return new Session(
                id,
                startDate,
                endDate,
                isCurrentSession
        );
    }
}
