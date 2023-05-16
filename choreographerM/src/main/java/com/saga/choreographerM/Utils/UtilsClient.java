package com.saga.choreographerM.Utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UtilsClient {
    public static Map<String, StatusRq> statusRequest = new HashMap<>();
    public static String createIdRequest(LocalDateTime created){
        return "id-request-" + created + "-random-" + Math.random();
    }
}
