package com.equipe4.audace.dto;

import com.equipe4.audace.model.User;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @Type(value = StudentDTO.class, name = "student"),
        @Type(value = EmployerDTO.class, name = "employer")
})
public abstract class UserDTO {
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phone;
    protected String email;
    protected String password;
    public abstract User fromDTO();
}
