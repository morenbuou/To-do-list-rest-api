package com.thoughtworks.restful.restful.controller.session;

import com.thoughtworks.restful.restful.model.User;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private static final Map<String, User> session = new HashMap<>();

    public static Map<String, User> getSession() {
        return session;
    }
}
