package com.thoughtworks.restful.restful.security;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getCookies() != null ? Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(HttpHeaders.AUTHORIZATION))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null) : null;
        if (!Strings.isNullOrEmpty(authorization)) {
            User user = loginService.getUserByAuthentication(authorization);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            user, "", ImmutableList.of()
                    )
            );
        }
        filterChain.doFilter(request, response);
    }
}
