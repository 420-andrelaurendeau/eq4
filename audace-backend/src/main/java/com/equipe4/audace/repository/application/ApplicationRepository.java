package com.equipe4.audace.repository.application;

import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findApplicationsByCv_StudentDepartmentId(Long studentId);
    Long countApplicationsByApplicationStatusAndOffer(Application.ApplicationStatus applicationStatus, Offer offer);
    List<Application> findAllByOffer(Offer offer);
    List<Application> findApplicationsByCv_Student(Student student);

    List<Application> findAllByApplicationStatusAndAndOffer_Department(Application.ApplicationStatus applicationStatus, Department department);
    List<Application> findApplicationsByCvStudentIdAndOfferId(Long studentId, Long offerId);
    List<Application> findAllByCvStudentDepartmentId(Long departmentId);
}
