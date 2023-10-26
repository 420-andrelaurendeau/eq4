package com.equipe4.audace.service;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.session.SessionDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends GenericUserService<User> {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public UserService(
            SaltRepository saltRepository,
            UserRepository userRepository,
            SessionRepository sessionRepository
    ) {
        super(saltRepository);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public Optional<UserDTO> getUser(Long id) {
        return userRepository.findById(id).map(User::toDTO);
    }

    public List<SessionDTO> getSessions() {
        return sessionRepository.findAll().stream().map(Session::toDTO).toList();
    }
}
