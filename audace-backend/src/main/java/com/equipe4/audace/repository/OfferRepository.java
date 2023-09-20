package com.equipe4.audace.repository;

import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByDepartment(Department department);
    List<Offer> findAllByEmployerId(Long id);
}
