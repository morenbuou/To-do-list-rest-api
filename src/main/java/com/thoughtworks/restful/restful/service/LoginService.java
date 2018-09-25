package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {

    public static final byte[] SECRET_KEY = "wjh".getBytes();

    @Autowired
    UserRepository userRepository;

    public User getUserByAuthentication(String signature) {
        final String url = "http://localhost:8082/user";

        RestTemplate template = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", signature);
        HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

        ResponseEntity rssResponse = template.exchange(url, HttpMethod.GET, requestEntity, User.class);
        return (User) rssResponse.getBody();
    }

    public String generateAuthentication(User user) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("userId", user.getId())
                .compact();
    }
}
