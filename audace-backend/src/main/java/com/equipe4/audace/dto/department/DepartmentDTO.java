package com.equipe4.audace.dto.department;

import com.equipe4.audace.model.department.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String code;
    private String name;

    public Department fromDTO() {
        return new Department(
                id,
                code,
                name
        );
    }
}
