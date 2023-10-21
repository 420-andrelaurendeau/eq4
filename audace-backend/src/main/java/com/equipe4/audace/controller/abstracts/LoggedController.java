package com.equipe4.audace.controller.abstracts;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public abstract class LoggedController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
}