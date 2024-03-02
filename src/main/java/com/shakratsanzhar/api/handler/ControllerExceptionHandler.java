package com.shakratsanzhar.api.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice(basePackages = "com.shakratsanzhar.api.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String handleForbiddenExceptions(AccessDeniedException ex) {
        return "error";
    }
}
