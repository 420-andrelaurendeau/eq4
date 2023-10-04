package com.equipe4.audace.controller;

import com.equipe4.audace.model.User;
import com.equipe4.audace.service.GenericUserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public abstract class GenericUserController <E extends User, T extends GenericUserService<E>> {
    protected final T service;
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    public GenericUserController(T service) {
        this.service = service;
    }
}
