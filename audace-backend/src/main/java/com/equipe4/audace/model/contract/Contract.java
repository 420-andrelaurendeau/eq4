package com.equipe4.audace.model.contract;

import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.application.Application;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_gen")
    @SequenceGenerator(name = "contract_gen", sequenceName = "contract_sec", allocationSize = 1)
    @Column(name = "contract_id")
    private Long id;

    private LocalTime startHour;
    private LocalTime endHour;
    private int totalHoursPerWeek;
    private double salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Employer supervisor;

    @OneToOne
    @ToString.Exclude
    private Application application;

    public ContractDTO toDTO(){
        return new ContractDTO(
                id,
                startHour.toString(),
                endHour.toString(),
                totalHoursPerWeek,
                salary,
                supervisor.toDTO(),
                application.toDTO()
        );
    }
}
