package com.equipe4.audace.controller.abstracts;

import com.equipe4.audace.model.User;
import com.equipe4.audace.service.GenericUserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GenericUserController <E extends User, T extends GenericUserService<E>> extends LoggedController {
    protected final T service;
}