package com.equipe4.audace.repository.offer;

import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByDepartment(Department department);
}