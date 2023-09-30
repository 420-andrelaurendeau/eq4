package com.equipe4.audace.controller;

import com.equipe4.audace.service.UserService;

import java.util.logging.Logger;

public abstract class GenericUserController <T extends UserService> {
    protected final T service;
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    public GenericUserController(T service) {
        this.service = service;
    }
}
