package com.equipe4.audace.model;

import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.model.department.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Manager extends User {
    @ManyToOne
    private Department department;

    @Builder(builderMethodName = "managerBuilder")
    public Manager(String firstname, String lastname, String email, String password, String address, String phone, Department department) {
        super(firstname, lastname, email, password, address, phone);
        this.department = department;
    }

    @Override
    public ManagerDTO toDTO() {
        return ManagerDTO.managerDTOBuilder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .address(address)
                .phone(phone)
                .departmentDTO(department.toDTO())
                .build();
    }
}
