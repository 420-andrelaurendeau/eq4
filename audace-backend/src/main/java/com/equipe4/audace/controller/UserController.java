package com.equipe4.audace.controller;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        Optional<List<UserDTO>> usersOptional = userService.getAllUsers();

        if (usersOptional.isPresent()) {
            List<UserDTO> usersDTO = usersOptional.get();
            return ResponseEntity.ok(usersDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        Optional<UserDTO> userOptional = userService.getUser(id);

        if (userOptional.isPresent()) {
            UserDTO userDTO = userOptional.get();
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}