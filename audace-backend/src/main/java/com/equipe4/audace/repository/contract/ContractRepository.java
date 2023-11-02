package com.equipe4.audace.repository.contract;

import com.equipe4.audace.model.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByApplicationId(Long applicationId);

    List<Contract> findAllByApplicationOfferDepartmentId(Long departmentId);
}
