package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController extends GenericUserController<User, UserService> {
    public UserController(UserService userService) {
        super(userService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        Optional<UserDTO> userOptional = service.getUser(id);

        if (userOptional.isPresent()) {
            UserDTO userDTO = userOptional.get();
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}