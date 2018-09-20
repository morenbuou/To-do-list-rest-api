package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.controller.session.Session;
import com.thoughtworks.restful.restful.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class LoginService {

    public User getUserByAuthentication(@RequestHeader String sessionId) {
        User user = new User();
        String userId = Session.getSession().get(sessionId).get("userId");
        user.setId(Long.valueOf(userId));
        return user;
    }
}
