package com.br.dias.hubspot_integration.Utils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OauthStateCache {
    private static final ConcurrentHashMap<String, String> stateCache = new ConcurrentHashMap<>();
    private static final int EXPIRATION_TIME = 5 * 60 * 2000;

    public static String generateAndStoreState(String sessionId){
        String state = UUID.randomUUID().toString();
        stateCache.put(sessionId,state);

        new Thread(()->{
            try{
                Thread.sleep(EXPIRATION_TIME);
                stateCache.remove(sessionId);
            } catch (InterruptedException interrupted) {
                interrupted.printStackTrace();
            }
        }).start();
        return state;
    }

    public static String getStateFromCache(String sessionId){
        return stateCache.get(sessionId);
    }

    public static void removeStateFromCache(String sessionId){
        stateCache.remove(sessionId);
    }
}
