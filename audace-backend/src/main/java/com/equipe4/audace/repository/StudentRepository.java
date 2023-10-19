package com.equipe4.audace.repository;

import com.equipe4.audace.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByStudentNumberOrEmail(String studentNumber, String email);
    Optional<Student> findByStudentNumber(String studentNumber);
}
