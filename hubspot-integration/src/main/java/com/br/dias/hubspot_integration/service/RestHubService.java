package com.br.dias.hubspot_integration.service;

import com.br.dias.hubspot_integration.config.RestHubClientConfig;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RestHubService {
    private Logger logger = Logger.getLogger(RestHubService.class.getName());

    public void authenticate(String authorizationCode){
      logger.info("Código de autorização recebido: " +  authorizationCode);
    }

}
