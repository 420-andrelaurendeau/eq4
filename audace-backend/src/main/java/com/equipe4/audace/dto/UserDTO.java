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
public abstract class UserDTO {
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phone;
    protected String email;
    protected String password;
    protected String type;
    public abstract User fromDTO();
}
