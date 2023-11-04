package com.equipe4.audace.dto.contract;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.model.contract.Contract;
import lombok.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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

    private EmployerDTO supervisor;
    private ApplicationDTO application;

    public Contract fromDTO(){
        return new Contract(
                id,
                LocalTime.parse(startHour, DateTimeFormatter.ofPattern("H:mm").localizedBy(Locale.ENGLISH)),
                LocalTime.parse(endHour, DateTimeFormatter.ofPattern("H:mm").localizedBy(Locale.ENGLISH)),
                totalHoursPerWeek,
                salary,
                supervisor.fromDTO(),
                application.fromDTO()
        );
    }


}
