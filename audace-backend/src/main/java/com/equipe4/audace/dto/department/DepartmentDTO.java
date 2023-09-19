package com.equipe4.audace.dto.department;

import com.equipe4.audace.model.department.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String code;
    private String name;

    public DepartmentDTO(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.code = department.getCode();
        this.name = department.getName();
    }

    public Department fromDto() {
        return new Department(id, code, name);
    }
}
