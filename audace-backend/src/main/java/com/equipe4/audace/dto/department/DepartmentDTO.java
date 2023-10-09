package com.equipe4.audace.dto.department;

import com.equipe4.audace.model.department.Department;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String code;
    private String name;


    @Builder(builderMethodName = "departmentDTOBuilder")
    public DepartmentDTO(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Department fromDto() {
        return Department.departmentBuilder()
                .code(code)
                .name(name)
                .build();
    }
}
