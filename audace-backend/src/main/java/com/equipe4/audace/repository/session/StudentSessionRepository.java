package com.equipe4.audace.repository.session;

import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.session.StudentSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentSessionRepository extends JpaRepository<StudentSession, Long> {
    List<StudentSession> findAllByStudentInAndSessionId(List<Student> students, Long sessionId);
}
