package com.br.dias.hubspot_integration.service;

import com.br.dias.hubspot_integration.exception.InvalidOauthTokenAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.logging.Logger;

@Service
public class RestHubTokenStoreService {
    private Logger logger = Logger.getLogger(RestHubTokenStoreService.class.getName());

    private String accessToken;
    private String refreshToken;
    private Instant expiresTime;

    public void saveToken(String accessToken, String refreshToken, int expiresInSecond) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresTime = Instant.now().plusSeconds(expiresInSecond - 60);
    }

    public String getAccessToken() {
        if (isTokenExpired()) {
            logger.warning("Tentaiva de uso de token expirado.");
            throw new InvalidOauthTokenAuthenticationException("O token de acesso expirou. Faça uma nova autenticação");
        }
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public boolean isTokenExpired() {
        return expiresTime == null || Instant.now().isAfter(expiresTime);
    }

    public void cleanTokens() {
        this.accessToken = null;
        this.refreshToken = null;
        this.expiresTime = null;
    }

    public String getTokenStatus() {
        if (accessToken == null) {
            return "No token stored";
        }
        return isTokenExpired() ? "Token expired." : "Token valid.";
    }
}
