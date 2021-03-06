package com.thoughtworks.restful.restful.client;

import com.thoughtworks.restful.restful.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userService")
public interface UserClient {

    @GetMapping("/users/authentication")
    User getUser(@RequestHeader("Authorization") String authorization);
}
