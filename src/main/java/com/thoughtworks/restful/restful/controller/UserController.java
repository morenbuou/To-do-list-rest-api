package com.thoughtworks.restful.restful.controller;

import com.thoughtworks.restful.restful.controller.Exception.NotFoundException;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register")
    public void registerUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody User user) throws NotFoundException {
        User existUser = userService.getUserByName(user.getUsername());
        if (existUser == null) {
            throw new NotFoundException();
        }
        if (!existUser.getPassword().equals(user.getPassword())) {
            throw new NotFoundException();
        }
        return;
    }
}
