package com.br.dias.hubspot_integration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContactCreationException extends RuntimeException {
    public ContactCreationException(String message) {
        super(message);
    }
}
