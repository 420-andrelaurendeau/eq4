package com.equipe4.audace.model;

import com.equipe4.audace.dto.DepartmentDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String name;

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public DepartmentDTO toDto() {
        return new DepartmentDTO(id, code, name);
    }
}
