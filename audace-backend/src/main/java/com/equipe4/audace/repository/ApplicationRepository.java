package com.equipe4.audace.repository;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Application;
import com.equipe4.audace.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findApplicationsByStudentId(Long studentId);
    Long countApplicationsByApplicationStatusAndOffer(Application.ApplicationStatus applicationStatus, Offer offer);
}