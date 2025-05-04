package com.br.dias.hubspot_integration.service;

import com.br.dias.hubspot_integration.exception.InvalidOauthTokenAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RestHubService {
    private Logger logger = Logger.getLogger(RestHubService.class.getName());


    public void authenticate(String authorizationCode) {
        isValidAuthorization(authorizationCode);
        logger.info("Código de autorização recebido: " + authorizationCode);
    }

    private static void isValidAuthorization(String authorizationCode) {
        if(authorizationCode == null || authorizationCode.isBlank()){
            throw new InvalidOauthTokenAuthenticationException("Código de autorização inválido ou ausente");
        }
    }

}
