package com.equipe4.audace.model;

import com.equipe4.audace.model.department.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserWithDepartment extends User {
    @ManyToOne
    protected Department department;

    public UserWithDepartment(
            Long id,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String address,
            Department department
    ) {
        super(id, firstName, lastName, email, password, address, phone);
        this.department = department;
    }
}
