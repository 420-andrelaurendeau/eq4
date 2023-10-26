package com.equipe4.audace.repository.session;

import com.equipe4.audace.model.session.StudentSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSessionRepository extends JpaRepository<StudentSession, Long> {
}
