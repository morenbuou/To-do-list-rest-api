package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public static final byte[] SECRET_KEY = "wjh".getBytes();

    @Autowired
    UserRepository userRepository;

    public User getUserByAuthentication(String signature) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(signature)
                .getBody();

        Long id = claims.get("userId", Long.class);
        return userRepository.findOne(id);
    }

    public String generateAuthentication(User user) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("userId", user.getId())
                .compact();
    }
}
