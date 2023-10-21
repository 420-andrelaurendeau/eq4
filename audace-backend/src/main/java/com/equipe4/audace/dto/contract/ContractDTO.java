package com.equipe4.audace.dto.contract;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.model.contract.Contract;
import lombok.*;

import java.time.LocalTime;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long id;
    private String departmentName;
    private LocalTime startHour;
    private LocalTime endHour;
    private int totalHoursPerWeek;
    private double salary;
    private String internTasksAndResponsibilities;

    private EmployerDTO supervisor;
    private ApplicationDTO application;

    public ContractDTO(Long id, String departmentName, LocalTime startHour, LocalTime endHour, int totalHoursPerWeek, double salary, String internTasksAndResponsibilities, EmployerDTO supervisor, ApplicationDTO application) {
        this.id = id;
        this.departmentName = departmentName;
        this.startHour = startHour;
        this.endHour = endHour;
        this.totalHoursPerWeek = totalHoursPerWeek;
        this.salary = salary;
        this.internTasksAndResponsibilities = internTasksAndResponsibilities;
        this.supervisor = supervisor;
        this.application = application;
    }

    public Contract fromDTO(){
        return new Contract(
                id,
                departmentName,
                startHour,
                endHour,
                totalHoursPerWeek,
                salary,
                internTasksAndResponsibilities,
                supervisor.fromDTO(),
                application.fromDTO()
        );
    }


}
