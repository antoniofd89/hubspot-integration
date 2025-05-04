package com.br.dias.hubspot_integration.controller;

import com.br.dias.hubspot_integration.DTO.RestHubContactDTO;
import com.br.dias.hubspot_integration.service.RestHubContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class RestHubContactController {
    private final RestHubContactService hubContactService;

    @Autowired
    public RestHubContactController(RestHubContactService hubContactService) {
        this.hubContactService = hubContactService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createContact(@RequestBody RestHubContactDTO request) {

        ResponseEntity<String> response = hubContactService.createContact(
                request.getAccessToken(),
                request.getEmail(),
                request.getFirstname(),
                request.getLastname(),
                request.getPhone()
        );
        return response;
    }

}
