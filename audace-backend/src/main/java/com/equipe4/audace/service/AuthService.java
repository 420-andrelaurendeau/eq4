package com.equipe4.audace.service;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.User;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.security.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public Optional<UserDTO> login(LoginRequest loginRequest) {
        String identification = loginRequest.getIdentification();
        String password = loginRequest.getPassword();

        Optional<User> user = userRepository.findByEmailAndPassword(identification, password);

        if (user.isEmpty()) {
            return studentRepository.findByStudentNumberAndPassword(identification, password).map(Student::toDTO);
        }

        return user.map(User::toDTO);
    }
}
