package com.br.dias.hubspot_integration.exception.handler;

import com.br.dias.hubspot_integration.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@RequestMapping
public class RestHubExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidOauthTokenAuthenticationException.class)
    public ResponseEntity<RestHubExceptionResponse> handleInvalidOauthTokenException(Exception ex, WebRequest request) {
        RestHubExceptionResponse response = new RestHubExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<RestHubExceptionResponse> handleTokenRefreshException(Exception ex, WebRequest request) {
        RestHubExceptionResponse response = new RestHubExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HubspotApiException.class)
    public ResponseEntity<RestHubExceptionResponse> handleHubspotApiException(Exception ex, WebRequest request) {
        RestHubExceptionResponse response = new RestHubExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ContactCreationException.class)
    public ResponseEntity<RestHubExceptionResponse> handleContactCreationException(Exception ex, WebRequest request) {
        RestHubExceptionResponse response = new RestHubExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}