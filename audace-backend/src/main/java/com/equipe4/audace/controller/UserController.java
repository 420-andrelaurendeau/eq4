package com.equipe4.audace.controller;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE }, allowedHeaders = { "Content-Type", "Authorization" })
@RestController
@RequestMapping("/users")
public class UserController extends GenericUserController<UserService>{
    public UserController(UserService userService) {
        super(userService);
    }

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        return this.service.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<UserDTO> getUser(@PathVariable long id) {
        return service.getUser(id);
    }
}