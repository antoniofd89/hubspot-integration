package com.br.dias.hubspot_integration.exception;

public class InvalidOauthTokenAuthenticationException extends RuntimeException {
  public InvalidOauthTokenAuthenticationException(String message) {
    super(message);
  }
}
