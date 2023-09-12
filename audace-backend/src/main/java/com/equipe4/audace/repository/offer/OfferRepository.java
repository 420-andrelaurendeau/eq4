package com.equipe4.audace.repository.offer;

import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<Offer> findByDepartment(Department department);
}
