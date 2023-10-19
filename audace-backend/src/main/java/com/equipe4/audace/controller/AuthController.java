package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.LoggedController;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.security.jwt.TimedJwt;
import com.equipe4.audace.security.login.LoginRequest;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import com.equipe4.audace.service.auth.AuthService;
import com.equipe4.audace.utils.JwtManipulator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController extends LoggedController {
    private final AuthService authService;
    private final EmployerService employerService;
    private final StudentService studentService;
    private final JwtManipulator jwtManipulator;

    @PostMapping("/login")
    public ResponseEntity<TimedJwt> login(@RequestBody LoginRequest loginRequest) {
        logger.info("login");

        Optional<UserDTO> loggedUser = authService.login(loginRequest);

        if (loggedUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        TimedJwt jwt = jwtManipulator.generateToken(loggedUser.get());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup/student/{departmentCode}")
    public ResponseEntity<HttpStatus> createStudent(@RequestBody StudentDTO studentDTO, @PathVariable String departmentCode) {
        logger.info("createStudent");
        return studentService.createStudent(studentDTO, departmentCode)
                .map(student -> new ResponseEntity<HttpStatus>(HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/signup/employer")
    public ResponseEntity<HttpStatus> createEmployer(@RequestBody EmployerDTO employerDTO){
        logger.info("createEmployer");
        return employerService.createEmployer(employerDTO)
                .map(employer -> new ResponseEntity<HttpStatus>(HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
