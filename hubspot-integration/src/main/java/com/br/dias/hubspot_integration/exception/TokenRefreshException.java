package com.br.dias.hubspot_integration.exception;

public class TokenRefreshException extends RuntimeException {
  public TokenRefreshException(String message) {
    super(message);
  }
}
