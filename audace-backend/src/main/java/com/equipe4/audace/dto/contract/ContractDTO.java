package com.equipe4.audace.dto.contract;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.model.contract.Contract;
import lombok.*;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long id;
    private String startHour;
    private String endHour;
    private int totalHoursPerWeek;
    private double salary;
    private String internTasksAndResponsibilities;

    private EmployerDTO supervisor;
    private ApplicationDTO application;

    public Contract fromDTO(){
        return new Contract(
                id,
                LocalTime.parse(startHour),
                LocalTime.parse(endHour),
                totalHoursPerWeek,
                salary,
                internTasksAndResponsibilities,
                supervisor.fromDTO(),
                application.fromDTO()
        );
    }


}
