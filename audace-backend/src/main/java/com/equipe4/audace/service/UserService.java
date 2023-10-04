package com.equipe4.audace.service;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //TODO : Spring Security Password
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(User::toDTO).toList();
    }

    public Optional<UserDTO> getUser(long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(User::toDTO);
    }
}
