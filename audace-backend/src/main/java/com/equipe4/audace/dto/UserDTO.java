package com.equipe4.audace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserDTO {
    protected Long id;
    protected String email;
    protected String password;
}
