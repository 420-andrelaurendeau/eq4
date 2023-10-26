package com.equipe4.audace.repository;

import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findApplicationsByCv_StudentDepartmentId(Long studentId);
    Long countApplicationsByApplicationStatusAndOffer(Application.ApplicationStatus applicationStatus, Offer offer);
    List<Application> findApplicationsByApplicationStatusAndCv_StudentDepartmentId(Application.ApplicationStatus applicationStatus, Long departmentId);
}