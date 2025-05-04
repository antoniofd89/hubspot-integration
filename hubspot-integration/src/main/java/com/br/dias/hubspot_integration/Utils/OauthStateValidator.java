package com.br.dias.hubspot_integration.Utils;

import com.br.dias.hubspot_integration.exception.InvalidOauthTokenAuthenticationException;

public class OauthStateValidator {

    public static void validateStateOrThrow(String sessionId, String receivedState) {
        String cachedState = OauthStateCache.getStateFromCache(sessionId);
        if (cachedState == null || !cachedState.equals(receivedState)) {
            throw new InvalidOauthTokenAuthenticationException("Estado de autenticação inválido ou ausente");

        }
        OauthStateCache.removeStateFromCache(sessionId);
    }
}
