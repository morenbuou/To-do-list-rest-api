package com.thoughtworks.restful.restful.controller;

import com.thoughtworks.restful.restful.controller.Exception.NotFoundException;
import com.thoughtworks.restful.restful.controller.session.Session;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.service.TodoService;
import com.thoughtworks.restful.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TodoService todoService;

    @PostMapping(value = "/register")
    public void registerUser(@RequestBody User user) throws NotFoundException {
        User existUser = userService.getUserByName(user.getUsername());
        if (existUser != null) {
            throw new NotFoundException();
        }
        userService.createUser(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user) throws NotFoundException {
        String sessionId = doLogin(user);

        return ResponseEntity.ok().header("sessionId", sessionId).build();
    }

    private String doLogin(@RequestBody User user) throws NotFoundException {
        User existUser = userService.getUserByName(user.getUsername());
        if (existUser == null) {
            throw new NotFoundException();
        }
        if (!existUser.getPassword().equals(user.getPassword())) {
            throw new NotFoundException();
        }

        String sessionId = UUID.randomUUID().toString();
        Map<String, String> token = new HashMap<>();
        token.put("userId", String.valueOf(user.getId()));
        Session.getSession().put(sessionId, token);
        return sessionId;
    }
}
