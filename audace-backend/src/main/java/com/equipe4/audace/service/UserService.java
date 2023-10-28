package com.equipe4.audace.service;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.session.SessionDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import com.equipe4.audace.utils.SessionManipulator;
import io.micrometer.observation.ObservationFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends GenericUserService<User> {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SessionManipulator sessionManipulator;

    public UserService(
            SaltRepository saltRepository,
            UserRepository userRepository,
            SessionRepository sessionRepository,
            SessionManipulator sessionManipulator
    ) {
        super(saltRepository);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.sessionManipulator = sessionManipulator;
    }

    public Optional<UserDTO> getUser(Long id) {
        return userRepository.findById(id).map(User::toDTO);
    }

    public List<SessionDTO> getSessions() {
        return sessionRepository.findAll().stream().map(Session::toDTO).toList();
    }

    public Optional<SessionDTO> getCurrentSession() {
        return Optional.of(sessionManipulator.getCurrentSession().toDTO());
    }

    public Optional<SessionDTO> getSession(Long sessionId) {
        return sessionRepository.findById(sessionId).map(Session::toDTO);
    }
}
