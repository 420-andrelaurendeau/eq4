package com.equipe4.audace.repository.offer;

import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByDepartment(Department department);
    List<Offer> findAllByEmployer(Employer employer);
    List<Offer> findAllByDepartmentAndOfferStatus(Department department, OfferStatus offerStatus);
}
