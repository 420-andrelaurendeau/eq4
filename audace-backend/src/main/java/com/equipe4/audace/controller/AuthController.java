package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.LoggedController;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.security.LoginRequest;
import com.equipe4.audace.service.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody LoginRequest loginRequest) {
        logger.info("login");

        Optional<UserDTO> loggedUser = authService.login(loginRequest);

        if (loggedUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<HttpStatus> doAThing() {
        logger.info("doAThing");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
