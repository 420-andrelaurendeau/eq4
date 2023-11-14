package com.equipe4.audace.repository;

import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findApplicationsByApplicationStatusAndOfferDepartmentId(Application.ApplicationStatus applicationStatus, Long departmentId);
    List<Application> findAllByOffer(Offer offer);
    List<Application> findApplicationsByCv_Student(Student student);
    List<Application> findApplicationsByCvStudentIdAndOfferId(Long studentId, Long offerId);
    List<Application> findAllByCvStudentDepartmentId(Long departmentId);
}
