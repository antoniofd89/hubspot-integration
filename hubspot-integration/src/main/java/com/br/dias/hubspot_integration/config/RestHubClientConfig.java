package com.br.dias.hubspot_integration.config;

import com.br.dias.hubspot_integration.service.RestHubTokenStoreService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Configuration
public class RestHubClientConfig {

    private Logger logger = Logger.getLogger(RestHubClientConfig.class.getName());

    private final RestHubTokenStoreService tokenStore;

    public RestHubClientConfig(RestHubTokenStoreService tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Bean
    @Qualifier("hubspotRestClient")
    RestTemplate hubRestClient() {
        logger.info("Initializing RestClient");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution)->{
            String accessToken = tokenStore.getAccessToken();
            request.getHeaders().add("Authorization","Bearer " + accessToken);
            return execution.execute(request,body);
        });

        return restTemplate;
    }
}