package com.example.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Defines an exception to be thrown when an entity is not found.
 */
public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String entity) {
        super(HttpStatus.NOT_FOUND, entity + " not found");
    }
}
