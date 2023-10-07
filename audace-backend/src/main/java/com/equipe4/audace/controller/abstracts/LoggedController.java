package com.equipe4.audace.controller.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.logging.Logger;

@AllArgsConstructor
public abstract class LoggedController {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
}
