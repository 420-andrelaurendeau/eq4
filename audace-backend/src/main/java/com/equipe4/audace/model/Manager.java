package com.equipe4.audace.model;

import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.model.department.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Manager extends User {
    private Department department;

    public Manager(Long id,
                   String firstname,
                   String lastname,
                   String email,
                   String password,
                   String address,
                   String phone,
                   Department department) {
        super(id, firstname, lastname, email, password, address, phone);
        this.department = department;
    }

    public ManagerDTO toDTO() {
        return new ManagerDTO(
                id,
                getFirstName(),
                getLastName(),
                email,
                address,
                phone,
                password,
                department.toDto()
        );
    }
}
