package com.equipe4.audace.controller;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE }, allowedHeaders = { "Content-Type", "Authorization" })
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
    public List<UserDTO> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<UserDTO> getUser(@PathVariable long id) {
        return userService.getUser(id);
    }
}