package com.br.dias.hubspot_integration.controller;

import com.br.dias.hubspot_integration.DTO.RestHubTokenResponseDTO;
import com.br.dias.hubspot_integration.service.RestHubTokenStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/oauth")
public class RestHubController {

    @Value("${security.oauth2.client.registration.hubspot.client-id:client-id}")
    private String clientId = "client-id";

    @Value("${security.oauth2.client.registration.hubspot.scope:scope}")
    private String scope = "scope";

    @Value("${security.oauth2.client.registration.hubspot.redirect-uri:redirect-uri}")
    private String redirectUri = "redirect-uri";

    @Value("${security.oauth2.client.registration.hubspot.client-secret:client-secret}")
    private String clientSecret;

    @Autowired
    private RestHubTokenStoreService tokenStore;

    @GetMapping("/authorize/")
    public ResponseEntity<Void> authorize() {

        String hubspotUrl = UriComponentsBuilder
                .fromHttpUrl("https://app.hubspot.com/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .build()
                .toUriString();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(hubspotUrl)).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callback(@RequestParam String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formulario = new LinkedMultiValueMap<>();
        formulario.add("grant_type", "authorization_code");
        formulario.add("client_id", clientId);
        formulario.add("client_secret", clientSecret);
        formulario.add("redirect_uri", redirectUri);
        formulario.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formulario, headers);

        ResponseEntity<RestHubTokenResponseDTO> response = restTemplate.postForEntity(
                "https://api.hubapi.com/oauth/v1/token", request, RestHubTokenResponseDTO.class
        );

        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            RestHubTokenResponseDTO tokenDTO = response.getBody();
            tokenStore.saveToken(
                    tokenDTO.getAccessToken(),
                    tokenDTO.getRefreshToken(),
                    Integer.parseInt(tokenDTO.getExpiresIn())
            );
        }
        return ResponseEntity.ok("Access Token Response: \n" + response.getBody());
    }
}