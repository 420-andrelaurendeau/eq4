package com.equipe4.audace.repository.contract;

import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByApplication(Application application);
    List<Contract> findAllByApplicationCvStudentDepartmentId(Long departmentId);
    List<Contract> findAllByApplication_Offer_Department(Department department);
}
