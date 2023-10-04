package com.equipe4.audace.controller;

import com.equipe4.audace.model.User;
import com.equipe4.audace.service.GenericUserService;

import java.util.logging.Logger;

public abstract class GenericUserController <E extends User, T extends GenericUserService<E>> {
    protected final T service;
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    public GenericUserController(T service) {
        this.service = service;
    }
}
