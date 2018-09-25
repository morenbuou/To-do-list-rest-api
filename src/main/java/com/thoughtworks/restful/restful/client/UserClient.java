package com.thoughtworks.restful.restful.client;

import com.thoughtworks.restful.restful.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "users", url = "localhost:8082")
public interface UserClient {

    @GetMapping("/user")
    User getUser(@RequestHeader("Authorization") String authorization);
}
