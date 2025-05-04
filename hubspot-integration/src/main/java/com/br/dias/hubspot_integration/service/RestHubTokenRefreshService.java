package com.br.dias.hubspot_integration.service;

import com.br.dias.hubspot_integration.DTO.RestHubTokenResponseDTO;
import com.br.dias.hubspot_integration.exception.HubspotApiException;
import com.br.dias.hubspot_integration.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class RestHubTokenRefreshService {
    private final RestHubTokenStoreService tokenStore;
    private Logger logger = Logger.getLogger(RestHubTokenRefreshService.class.getName());

    @Value("${security.oauth2.client.registration.hubspot.client-id:client-id}")
    private String clientId = "client-id";

    @Value("${security.oauth2.client.registration.hubspot.client-secret:client-secret}")
    private String clientSecret;

    public RestHubTokenRefreshService(RestHubTokenStoreService tokenStore) {
        this.tokenStore = tokenStore;
    }

    public void refreshIfNeeded() {
        if (!tokenStore.isTokenExpired()) {
            return;
        }

        logger.warning("Token expirado. Tentando renovar com refresh token..");

        String refreshToken = tokenStore.getRefreshToken();

        if (refreshToken == null) {
            tokenStore.cleanTokens();
            throw new TokenRefreshException("Resfresh token não disponivel para renovar o access token");
        }
        logger.info("Access token expirado. Tentando renovar com refresh token...");

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grand_type", "refresh_token");
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("refresh_token", refreshToken);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<RestHubTokenResponseDTO> response = restTemplate.postForEntity(
                    "https://api.hubapi.com/oauth/v1/token", request, RestHubTokenResponseDTO.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                RestHubTokenResponseDTO token = response.getBody();
                tokenStore.saveToken(
                        token.getAccessToken(),
                        token.getRefreshToken(),
                        Integer.parseInt(token.getExpiresIn())
                );
                logger.info("Novo access token obtido com sucesso");
            } else {
                throw new HubspotApiException("Erro da API hubspot ao renovar o token: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.severe("Erro HTTP ao chamar a API do Hubsopt" + ex.getMessage());
            throw new HubspotApiException("Erro Http: " + ex.getStatusCode());

        } catch (Exception e) {
            logger.severe("Erro inesperado: " + e.getMessage());
            throw new TokenRefreshException("Erro ao renovar access token");

        }
    }
}