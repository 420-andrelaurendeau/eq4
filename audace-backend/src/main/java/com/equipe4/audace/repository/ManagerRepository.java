package com.equipe4.audace.repository;

import com.equipe4.audace.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findManagersByDepartmentId(Long departmentId);
}
