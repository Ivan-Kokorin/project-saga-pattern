package com.saga.choreographerM.Utils;

import java.time.LocalDateTime;

public class UtilsClient {
    public static String createIdRequest(LocalDateTime created){
        return "id-request-" + created + "-random-" + Math.random();
    }
}
