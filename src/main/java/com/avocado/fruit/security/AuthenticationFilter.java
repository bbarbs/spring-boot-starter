package com.avocado.fruit.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.avocado.fruit.dto.login.LoginRequest;
import com.avocado.fruit.dto.login.LoginResponse;
import com.avocado.fruit.exception.config.ErrorMessage;
import com.avocado.fruit.exception.config.RestExceptionMessage;
import com.avocado.fruit.service.JwtService;
import com.avocado.fruit.util.JwtConfig;
import com.avocado.fruit.util.JwtUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationManager authManager;
    private JwtService jwtService;
    private JwtUtil jwtUtil;

    public AuthenticationFilter(AuthenticationManager authManager, JwtService jwtService, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest req = this.getCredentials(request);
            return authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    req.getEmail(),
                    req.getPassword()
            ));
        } catch (Exception e) {
            try {
                response.getWriter().write(new Gson().toJson(createExceptionMessage(e.getMessage())));
                return null;
            } catch (IOException ex) {
                throw new RuntimeException(e);
            }
        }
    }

    private RestExceptionMessage createExceptionMessage(String message) {
        return RestExceptionMessage.builder()
                .timestamp(new Date())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(new ErrorMessage(message))
                .build();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) {
        try {
            SecurityContextHolder.getContext().setAuthentication(auth);
            SecretKey secretKey = jwtUtil.generateKey();
            String username = ((User) auth.getPrincipal()).getUsername();
            String token = jwtUtil.createToken(username, JwtConfig.JWT_EXPIRATION, secretKey);
            jwtService.setTokenSecretKey(token, secretKey);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            String json = new ObjectMapper().writeValueAsString(loginResponse);
            response.getWriter().write(new Gson().toJson(loginResponse));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LoginRequest getCredentials(HttpServletRequest request) {
        LoginRequest auth = null;
        try {
            auth = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return auth;
    }
}
