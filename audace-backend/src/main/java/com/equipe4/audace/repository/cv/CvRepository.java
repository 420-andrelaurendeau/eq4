package com.equipe4.audace.repository.cv;

import com.equipe4.audace.model.cv.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CvRepository extends JpaRepository<Cv, Long> {
    List<Cv> findAllByStudentId(Long studentId);
    List<Cv> findAllByStudentDepartmentId(Long departmentId);
}
