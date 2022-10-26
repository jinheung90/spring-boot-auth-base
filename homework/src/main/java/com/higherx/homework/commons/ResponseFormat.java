package com.higherx.homework.commons;


import java.util.HashMap;
import java.util.Map;

public class ResponseFormat {

    public static <T> Map<String, Object> responseTrue(T t) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", t);
        result.put("success", true);
        return result;
    }

}
