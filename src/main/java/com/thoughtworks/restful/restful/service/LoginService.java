package com.thoughtworks.restful.restful.service;

import com.google.common.base.Strings;
import com.thoughtworks.restful.restful.controller.session.Session;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public User getUserByAuthentication(String sessionId) {
        User user = null;
        if (!Strings.isNullOrEmpty(sessionId)) {
            Long userId = Long.valueOf(Session.getSession().get(sessionId).get("userId"));
            user = userRepository.findOne(userId);
        }
        return user;
    }

    public String generateAuthentication(User user) {
        String sessionId = UUID.randomUUID().toString();
        Map<String, String> authentication = new HashMap<>();
        authentication.put("userId", String.valueOf(user.getId()));
        Session.getSession().put(sessionId, authentication);
        return sessionId;
    }
}
