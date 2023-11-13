package com.equipe4.audace.dto.contract;

import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.Supervisor;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.contract.Signature;
import jakarta.persistence.Embedded;
import lombok.*;

import java.time.LocalDate;
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

    private Signature<Student> studentSignature;
    private Signature<Employer> employerSignature;
    private Signature<Manager> managerSignature;

    public ContractDTO(Long id, String startHour, String endHour, int totalHoursPerWeek, double salary, Supervisor supervisor, ApplicationDTO application) {
        this.id = id;
        this.startHour = startHour;
        this.endHour = endHour;
        this.totalHoursPerWeek = totalHoursPerWeek;
        this.salary = salary;
        this.supervisor = supervisor;
        this.application = application;
    }

    public Contract fromDTO(){
        return new Contract(
                id,
                LocalTime.parse(startHour, DateTimeFormatter.ofPattern("H:mm").localizedBy(Locale.ENGLISH)),
                LocalTime.parse(endHour, DateTimeFormatter.ofPattern("H:mm").localizedBy(Locale.ENGLISH)),
                totalHoursPerWeek,
                salary,
                supervisor,
                application.fromDTO(),
                studentSignature,
                employerSignature,
                managerSignature
        );
    }


}
