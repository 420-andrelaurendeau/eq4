package com.equipe4.audace.repository.security;

import com.equipe4.audace.model.User;
import com.equipe4.audace.model.security.Salt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaltRepository extends JpaRepository<Salt, Long> {
    Optional<Salt> findByUser(User user);
}
