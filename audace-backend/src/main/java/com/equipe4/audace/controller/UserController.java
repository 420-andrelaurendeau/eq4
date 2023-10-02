package com.equipe4.audace.controller;

import com.equipe4.audace.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController extends GenericUserController<UserService>{
    public UserController(UserService userService) {
        super(userService);
    }
}
