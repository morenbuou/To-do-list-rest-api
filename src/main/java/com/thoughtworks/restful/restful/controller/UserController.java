package com.thoughtworks.restful.restful.controller;

import com.thoughtworks.restful.restful.controller.Exception.ForbiddenException;
import com.thoughtworks.restful.restful.controller.Exception.NotFoundException;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.service.LoginService;
import com.thoughtworks.restful.restful.service.TodoService;
import com.thoughtworks.restful.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    TodoService todoService;

    @PostMapping(value = "/register")
    public void registerUser(@RequestBody User user) throws ForbiddenException {
        User existUser = userService.getUserByName(user.getUsername());
        if (existUser == null) {
            throw new ForbiddenException();
        }
        userService.createUser(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user) throws NotFoundException, ForbiddenException {
        String sessionId = doLogin(user);

        return ResponseEntity.ok().header("sessionId", sessionId).build();
    }

    private String doLogin(User user) throws NotFoundException, ForbiddenException {
        User existUser = userService.getUserByName(user.getUsername());
        if (existUser == null) {
            throw new NotFoundException();
        }
        if (!existUser.getPassword().equals(user.getPassword())) {
            throw new ForbiddenException();
        }
        return loginService.generateAuthentication(user);
    }

}
