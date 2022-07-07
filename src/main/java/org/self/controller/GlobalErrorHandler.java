package org.self.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { DisabledException.class, BadCredentialsException.class })
    protected ResponseEntity<String> handleError(Exception ex, WebRequest request) {
        String bodyOfResponse = "System error";
        request.setAttribute("error", ex.getMessage(),WebRequest.SCOPE_REQUEST);
        return new ResponseEntity<String>(bodyOfResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

