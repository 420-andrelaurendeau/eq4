package com.equipe4.audace.service;

import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department findDepartmentByCode(String departmentCode){
        Optional<Department> departmentOptional = departmentRepository.findByCode(departmentCode);
        if (!departmentOptional.isEmpty()) throw new IllegalArgumentException("Department doesn't exists");
        return departmentOptional.get();
    }
}
