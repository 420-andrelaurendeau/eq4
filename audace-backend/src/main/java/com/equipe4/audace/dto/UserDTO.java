package com.equipe4.audace.dto;

import com.equipe4.audace.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    protected Long id;
    protected String email;
    protected String password;

    public UserDTO userToDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getPassword());
    }
    public List<UserDTO> usersToDTO(List<User> users) {
        return users.stream()
                .map(this::userToDTO)
                .collect(Collectors.toList());
    }
}
