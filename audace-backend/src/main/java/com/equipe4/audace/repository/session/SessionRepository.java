package com.equipe4.audace.repository.session;

import com.equipe4.audace.model.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT s FROM Session s " +
            "WHERE :chosenDate BETWEEN s.startDate AND s.endDate")
    List<Session> findAllByDateBetween(LocalDate chosenDate);

    Optional<Session> findFirstByIsCurrentSessionTrue();
}
