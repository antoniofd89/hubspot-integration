package com.br.dias.hubspot_integration.controller;

import com.br.dias.hubspot_integration.DTO.RestHubTokenResponseDTO;
import com.br.dias.hubspot_integration.Utils.OauthStateValidator;
import com.br.dias.hubspot_integration.service.RestHubTokenStoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RestHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RestTemplate restTemplate;

    @MockitoBean
    private RestHubTokenStoreService tokenStoreService;

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(tokenStoreService).saveToken(
                Mockito.any(),
                Mockito.any(),
                Mockito.anyInt());
    }

    @Test
    public void shouldRedirectToHubspotAuthorization() throws Exception {
        mockMvc.perform(get("/oauth/authorize/")
                .sessionAttr("JSESSIONID", "test-session"))
                .andExpect(status().isFound())
                .andExpect(header().string("location", Matchers.containsString("https://app.hubspot.com/oauth/authorize")))
                .andExpect(header().string("location", Matchers.containsString("client_id=")))
                .andExpect(header().string("location", Matchers.containsString("state=")))
                        .andExpect(header().string("location", Matchers.containsString("redirect_uri")))
                .andExpect(header().string("location", Matchers.containsString("scope=")));
    }

    @Test
    public void shouldHandlerCallbackAndStoreTokenSuccess() throws Exception {
        String code = "test-mock-code";
        String state = "test-mock-state";

        RestHubTokenResponseDTO tokenDTO = new RestHubTokenResponseDTO();
        tokenDTO.setAccessToken("access-token");
        tokenDTO.setRefreshToken("refresh-token");
        tokenDTO.setExpiresIn("1800");

        ResponseEntity<RestHubTokenResponseDTO> responseMock = ResponseEntity.ok(tokenDTO);

        Mockito.when(restTemplate.postForEntity(
                Mockito.eq("https://api.hubapi.com/oauth/v1/token"),
                Mockito.any(HttpEntity.class),
                Mockito.eq(RestHubTokenResponseDTO.class)
        )).thenReturn(responseMock);

        Mockito.mockStatic(OauthStateValidator.class).when(()->
                OauthStateValidator.validateStateOrThrow(Mockito.anyString(),Mockito.eq(state))
        ).thenAnswer(invocationOnMock -> null);

        mockMvc.perform(get("/oauth/callback")
                .param("code", code)
                .param("state", state)
                .sessionAttr("JSESSIONID", "test-session"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("access-token"))
                .andExpect(jsonPath("$.refresh_token").value("refresh-token"))
                .andExpect(jsonPath("$.expires_in").value("1800"));

        Mockito.verify(tokenStoreService).saveToken("access-token"
                ,"refresh-token",1800);
    }
}
