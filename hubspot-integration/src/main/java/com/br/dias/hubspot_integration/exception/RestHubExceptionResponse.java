package com.br.dias.hubspot_integration.exception;

import java.util.Date;

public record RestHubExceptionResponse(Date timestamp, String message, String details) {

}
