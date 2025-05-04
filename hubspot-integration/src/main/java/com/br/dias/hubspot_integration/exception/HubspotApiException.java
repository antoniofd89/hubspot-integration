package com.br.dias.hubspot_integration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class HubspotApiException extends RuntimeException {
    public HubspotApiException(String message) {
        super(message);
    }
}
