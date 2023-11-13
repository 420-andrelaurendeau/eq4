package com.equipe4.audace.repository;

import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByStudentNumberOrEmail(String studentNumber, String email);
    Optional<Student> findByStudentNumber(String studentNumber);
    Optional<Student> findByCv(Cv cv);
    List<Student> findAllByDepartmentId(Long departmentId);

    @Query("SELECT s FROM Student s " +
            "WHERE s.department.id = ?1 AND s.id NOT IN " +
            "(SELECT a.cv.student.id FROM Application a " +
            "WHERE a.offer.department.id = ?1)")
    List<Student> findAllWithoutApplicationsByDepartmentId(Long departmentId);
}
