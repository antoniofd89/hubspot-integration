package com.br.dias.hubspot_integration.service;

import com.br.dias.hubspot_integration.exception.HubspotApiException;
import com.br.dias.hubspot_integration.exception.InvalidOauthTokenAuthenticationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class RestHubContactService {
    private Logger logger = Logger.getLogger(RestHubContactService.class.getName());

    private final RestTemplate restTemplate;

    public RestHubContactService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> createContact(String accessToken, String email,
                                                String firstname, String lastname, String phone) {

        isValidAccessToken(accessToken);

        String urlContact = "https://api.hubapi.com/crm/v3/objects/contacts";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        String jsonBody = String.format(
                "{ \"properties\": { \"email\": \"%s\", \"firstname\": \"%s\", \"lastname\": \"%s\", \"phone\": \"%s\" } }",
                email, firstname, lastname, phone);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        try {
            return restTemplate.exchange(urlContact, HttpMethod.POST, entity, String.class);
        } catch (HttpServerErrorException | HttpClientErrorException ex) {
            logger.severe("Erro ao criar contato no Hubspot: " + ex.getResponseBodyAsString());
            throw new HubspotApiException("Erro ao criar contato: " + ex.getMessage());
        } catch (RestClientException e) {
            logger.severe("Erro de conexão ao tentar criar contato: " + e.getMessage());
            throw new HubspotApiException(" Erro ao conectar ao Hubspot: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Erro inesperado ao criar contato " + e.getMessage());
            throw new HubspotApiException(" Erro desconhecido ao criar contato: " + e.getMessage());
        }

    }

    private static void isValidAccessToken(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new InvalidOauthTokenAuthenticationException("Access token não fornecido");
        }
    }
}
