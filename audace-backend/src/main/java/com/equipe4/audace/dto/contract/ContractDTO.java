package com.equipe4.audace.dto.contract;

import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.model.Supervisor;
import com.equipe4.audace.model.contract.Contract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long id;
    private String startHour;
    private String endHour;
    private int totalHoursPerWeek;
    private double salary;

    private Supervisor supervisor;
    private ApplicationDTO application;

    public Contract fromDTO(){
        return new Contract(
                id,
                LocalTime.parse(startHour, DateTimeFormatter.ofPattern("H:mm").localizedBy(Locale.ENGLISH)),
                LocalTime.parse(endHour, DateTimeFormatter.ofPattern("H:mm").localizedBy(Locale.ENGLISH)),
                totalHoursPerWeek,
                salary,
                supervisor,
                application.fromDTO()
        );
    }


}
