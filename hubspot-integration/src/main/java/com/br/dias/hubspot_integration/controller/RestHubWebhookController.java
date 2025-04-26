package com.br.dias.hubspot_integration.controller;

import com.br.dias.hubspot_integration.DTO.RestHubEventWebhookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/webhook")
public class RestHubWebhookController {
    private final Logger logger = Logger.getLogger(RestHubWebhookController.class.getName());

    @PostMapping
    public ResponseEntity<?> handleWebhook(@RequestBody List<RestHubEventWebhookDTO> events) {
        logger.info("Recebendo webhook do hubspot com " + events.size());
        for (RestHubEventWebhookDTO event : events) {
            String eventType = event.getSubscriptionType();
            Long objectId = event.getObjectId();

            logger.info("Tipo do evento: " + eventType);
            logger.info("Dados recebidos: " + objectId);

            if ("contact.creation".equals(eventType)) {
                logger.info("Contato criado com ID: " + objectId);
            }
        }
        return ResponseEntity.ok().build();
    }
}
