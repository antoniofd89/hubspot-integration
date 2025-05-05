package com.br.dias.hubspot_integration.controller;

import com.br.dias.hubspot_integration.DTO.RestHubContactDTO;
import com.br.dias.hubspot_integration.service.RestHubContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestHubContactController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestHubContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RestHubContactService contactService;


    @Test
    public void shouldCreateContactSuccess() throws Exception {

        Mockito.when(contactService.createContact(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        )).thenReturn(ResponseEntity.ok("Contact Create"));

        RestHubContactDTO contactDTO = new RestHubContactDTO(
                "test_token",
                "test@example.com",
                "Joao test",
                "mock",
                "81945621987"
        );

        mockMvc.perform(post("/contacts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Contact Create"));

    }
}