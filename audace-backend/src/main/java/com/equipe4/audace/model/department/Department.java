package com.equipe4.audace.model.department;

import com.equipe4.audace.dto.department.DepartmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
    @SequenceGenerator(name = "department_gen", sequenceName = "department_sec", allocationSize = 1)
    @Column(name = "department_id")
    private Long id;
    private String code;
    private String name;

    public DepartmentDTO toDTO() {
        return DepartmentDTO.departmentDTOBuilder()
                .id(id)
                .code(code)
                .name(name)
                .build();
    }
}
