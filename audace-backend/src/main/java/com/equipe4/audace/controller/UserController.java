package com.equipe4.audace.controller;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        logger.info("createUser");
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return userService.createUser(userDTO)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/getAllUsers")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<UserDTO> getAllUsers() {
        logger.info("getAllUsers");
        return userService.getAllUsers();
    }
}
