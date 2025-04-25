package com.br.dias.hubspot_integration.mapper;

import com.br.dias.hubspot_integration.DTO.RestHubRequestDTO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RestHubRequestMapper {

    public MultiValueMap<String, String> parseRequestDTOToForm(RestHubRequestDTO dto) {
        MultiValueMap<String, String> formularioToDTO = new LinkedMultiValueMap<>();
        formularioToDTO.add("grant_type", dto.getGrant_type());
        formularioToDTO.add("client_id", dto.getClient_id());
        formularioToDTO.add("client_secret", dto.getClient_secret());
        formularioToDTO.add("redirect_uri", dto.getRedirect_uri());
        formularioToDTO.add("code", dto.getCode());

        return formularioToDTO;

    }
}
