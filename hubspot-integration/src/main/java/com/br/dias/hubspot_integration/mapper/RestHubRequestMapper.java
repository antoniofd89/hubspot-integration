package com.br.dias.hubspot_integration.mapper;

import com.br.dias.hubspot_integration.DTO.RestHubRequestDTO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RestHubRequestMapper {

    public MultiValueMap<String, String> parseRequestDTOToForm(RestHubRequestDTO dto) {
        MultiValueMap<String, String> formulario = new LinkedMultiValueMap<>();
        formulario.add("grant_type", dto.getGrant_type());
        formulario.add("client_id", dto.getClient_id());
        formulario.add("client_secret", dto.getClient_secret());
        formulario.add("redirect_uri", dto.getRedirect_uri());
        formulario.add("code", dto.getCode());

        return formulario;

    }
}
