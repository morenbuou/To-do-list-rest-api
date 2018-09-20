package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.controller.session.Session;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public User getUserByAuthentication(String sessionId) {
        return Session.getSession().get(sessionId);
    }

    public String generateAuthentication(User user) {
        String sessionId = UUID.randomUUID().toString();
        Session.getSession().put(sessionId, user);
        return sessionId;
    }
}
