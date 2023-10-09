package com.equipe4.audace.dto;

import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.model.Manager;
import lombok.*;

@Data
@NoArgsConstructor
public class ManagerDTO extends UserDTO {
    private DepartmentDTO departmentDTO;

    @Builder(builderMethodName = "managerDTOBuilder")
    public ManagerDTO(Long id, String firstName, String lastName, String email, String address, String phone, String password, DepartmentDTO departmentDTO) {
        super(id, firstName, lastName, address, phone, email, password);
        this.departmentDTO = departmentDTO;
    }

    @Override
    public Manager fromDTO() {
        return Manager.managerBuilder()
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .password(password)
                .address(address)
                .phone(phone)
                .department(departmentDTO.fromDto())
                .build();
    }
}
