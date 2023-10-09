package com.equipe4.audace.service.auth;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.security.login.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final SaltRepository saltRepository;

    public Optional<UserDTO> login(LoginRequest loginRequest) {
        String identification = loginRequest.getIdentification();
        Optional<User> optionalUser = userRepository.findByEmail(identification);

        if (optionalUser.isEmpty())
            optionalUser = studentRepository.findByStudentNumber(identification).map((student -> student));

        User user = optionalUser.orElseThrow();
        Salt salt = saltRepository.findByUser(user).orElseThrow();

        String pw = loginRequest.getPassword();
        String hashedPw = BCrypt.hashpw(pw, salt.getValue());

        if (!hashedPw.equals(user.getPassword()))
            return Optional.empty();

        return optionalUser.map(User::toDTO);
    }
}
