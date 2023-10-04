package com.equipe4.audace.repository.offer;

import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByDepartment(Department department);

    List<Offer> findAllByEmployer(Employer employer);
    List<Offer> findAllByDepartmentAndStatus(Department department, Status status);
}
