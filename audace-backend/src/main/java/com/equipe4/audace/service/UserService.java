package com.equipe4.audace.service;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    //TODO : Spring Security Password
    UserRepository userRepository;
    public void createUser(UserDTO userDTO) {

    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        UserDTO userDTO = new UserDTO();

        return userDTO.usersToDTO(users);
    }

    public UserDTO getUser(long id) {
        User user = userRepository.findById(id);
        UserDTO userDTO = new UserDTO();

        return userDTO.userToDTO(user);
    }



}
