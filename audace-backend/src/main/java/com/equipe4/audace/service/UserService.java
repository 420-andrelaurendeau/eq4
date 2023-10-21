package com.equipe4.audace.service;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService extends GenericUserService<User> {
    private final UserRepository userRepository;

    public UserService(SaltRepository saltRepository, UserRepository userRepository) {
        super(saltRepository);
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(User::toDTO).toList();
    }

    public Optional<UserDTO> getUser(Long id) {
        return userRepository.findById(id).map(User::toDTO);
    }
}
