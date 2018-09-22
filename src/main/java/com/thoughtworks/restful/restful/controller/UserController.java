package com.thoughtworks.restful.restful.controller;

import com.thoughtworks.restful.restful.controller.Exception.ForbiddenException;
import com.thoughtworks.restful.restful.controller.Exception.NotFoundException;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.service.LoginService;
import com.thoughtworks.restful.restful.service.TodoService;
import com.thoughtworks.restful.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    TodoService todoService;

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@RequestBody User user) {
        User existUser = userService.getUserByName(user.getUsername());
        if (existUser != null) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(HttpServletResponse response, @RequestBody User user) throws NotFoundException, ForbiddenException {
        String jwtToken = doLogin(user);
        response.addCookie(new Cookie(HttpHeaders.AUTHORIZATION, jwtToken));
        return ResponseEntity.ok().build();
    }

    private String doLogin(User user) throws NotFoundException, ForbiddenException {
        User existUser = userService.getUserByName(user.getUsername());
        if (existUser == null) {
            throw new NotFoundException();
        }
        if (!existUser.getPassword().equals(user.getPassword())) {
            throw new ForbiddenException();
        }
        return loginService.generateAuthentication(existUser);
    }

}
