package com.thoughtworks.restful.restful.controller.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private static final Map<String, Map<String, String>> session = new HashMap<>();

    public static Map<String, Map<String, String>> getSession() {
        return session;
    }
}
