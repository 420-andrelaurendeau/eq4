package com.equipe4.audace.dto;

import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.Manager;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManagerDTO extends UserDTO {
    private DepartmentDTO department;

    public ManagerDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      String address,
                      String phone,
                      String password,
                      DepartmentDTO department) {
        super(id, firstName, lastName, email, address, phone, password, "manager");
        this.department = department;
    }

    @Override
    public Manager fromDTO() {
        return new Manager(
                id,
                firstName,
                lastName,
                email,
                password,
                address,
                phone,
                department.toDto()
        );
    }
}
