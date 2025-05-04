package com.br.dias.hubspot_integration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidOauthTokenAuthenticationException extends AuthenticationException {

    public InvalidOauthTokenAuthenticationException(String message) {
        super(message);
    }
}
