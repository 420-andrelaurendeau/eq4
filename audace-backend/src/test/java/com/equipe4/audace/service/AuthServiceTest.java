package com.equipe4.audace.service;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.security.login.LoginRequest;
import com.equipe4.audace.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SaltRepository saltRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testLogin_happyPath_nonStudentNumberIdentification() {
        LoginRequest loginRequest = new LoginRequest("email@email.com", "password");
        User mockedUser = mock(User.class);
        Salt salt = createNewSalt(mockedUser);

        String hashedPw = BCrypt.hashpw("password", salt.getValue());

        when(userRepository.findByEmail(loginRequest.getIdentification())).thenReturn(Optional.of(mockedUser));
        when(saltRepository.findByUser(mockedUser)).thenReturn(Optional.of(salt));
        when(mockedUser.getPassword()).thenReturn(hashedPw);
        when(mockedUser.toDTO()).thenReturn(mock(UserDTO.class));

        Optional<UserDTO> userDTO = authService.login(loginRequest);

        assertThat(userDTO).isPresent();
    }

    @Test
    public void testLogin_happyPath_studentNumberIdentification() {
        LoginRequest loginRequest = new LoginRequest("123123", "password");
        Student mockedUser = mock(Student.class);
        Salt salt = createNewSalt(mockedUser);

        String hashedPw = BCrypt.hashpw("password", salt.getValue());

        when(userRepository.findByEmail(loginRequest.getIdentification())).thenReturn(Optional.empty());
        when(studentRepository.findByStudentNumber(loginRequest.getIdentification())).thenReturn(Optional.of(mockedUser));
        when(saltRepository.findByUser(mockedUser)).thenReturn(Optional.of(salt));
        when(mockedUser.getPassword()).thenReturn(hashedPw);
        when(mockedUser.toDTO()).thenReturn(mock(StudentDTO.class));

        Optional<UserDTO> userDTO = authService.login(loginRequest);

        assertThat(userDTO).isPresent();
    }

    @Test
    public void testLogin_invalidIdentification() {
        LoginRequest loginRequest = new LoginRequest("email@email.com", "password");

        when(userRepository.findByEmail(loginRequest.getIdentification())).thenReturn(Optional.empty());
        when(studentRepository.findByStudentNumber(loginRequest.getIdentification())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(loginRequest)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testLogin_invalidPassword() {
        LoginRequest loginRequest = new LoginRequest("email@email.com", "password");
        User mockedUser = mock(User.class);

        Salt salt = createNewSalt(mockedUser);

        when(userRepository.findByEmail(loginRequest.getIdentification())).thenReturn(Optional.of(mockedUser));
        when(saltRepository.findByUser(mockedUser)).thenReturn(Optional.of(salt));
        when(mockedUser.getPassword()).thenReturn("anIncorrectPassword");

        assertThat(authService.login(loginRequest)).isEmpty();
    }

    private Salt createNewSalt(User user) {
        String generatedSalt = BCrypt.gensalt();
        return new Salt(null, user, generatedSalt);
    }
}
