package com.equipe4.audace.model.contract;

import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.Supervisor;
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

    @Embedded
    private Supervisor supervisor;

    @OneToOne
    @ToString.Exclude
    private Application application;

    @OneToOne
    private Signature<Student> studentSignature;

    @OneToOne
    private Signature<Employer> employerSignature;

    @OneToOne
    private Signature<Manager> managerSignature;

    public Contract(Long id, LocalTime startHour, LocalTime endHour, int totalHoursPerWeek, double salary, Supervisor supervisor, Application application) {
        this.id = id;
        this.startHour = startHour;
        this.endHour = endHour;
        this.totalHoursPerWeek = totalHoursPerWeek;
        this.salary = salary;
        this.supervisor = supervisor;
        this.application = application;
    }

    public boolean isSignedBy(Class<?> signatoryClass){
        if(signatoryClass == Student.class){
            return studentSignature != null;
        } else if(signatoryClass == Employer.class){
            return employerSignature != null;
        } else if(signatoryClass == Manager.class){
            return managerSignature != null;
        } else {
            return false;
        }
    }

    public ContractDTO toDTO(){
        return new ContractDTO(
                id,
                startHour.toString(),
                endHour.toString(),
                totalHoursPerWeek,
                salary,
                supervisor,
                application.toDTO(),
                studentSignature != null ? studentSignature.getSignatureDate() : null,
                employerSignature != null ? employerSignature.getSignatureDate() : null,
                managerSignature != null ? managerSignature.getSignatureDate() : null
        );
    }
}
