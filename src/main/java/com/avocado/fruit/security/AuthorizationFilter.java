package com.avocado.fruit.security;

import com.avocado.fruit.exception.EmployeeNotFoundException;
import com.avocado.fruit.exception.config.ErrorCodes;
import com.avocado.fruit.exception.config.ErrorMessage;
import com.avocado.fruit.exception.config.RestExceptionMessage;
import com.avocado.fruit.service.JwtService;
import com.avocado.fruit.service.impl.EmployeeServiceImpl;
import com.avocado.fruit.util.HeaderConstant;
import com.avocado.fruit.util.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    private EmployeeServiceImpl employeeService;
    private JwtService jwtService;
    private JwtUtil jwtUtil;

    public AuthorizationFilter(AuthenticationManager authenticationManager, EmployeeServiceImpl employeeService,
                               JwtService jwtService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.employeeService = employeeService;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) {
        try {
            String header = request.getHeader(HeaderConstant.AUTHORIZATION_HEADER_STRING);
            if (header == null || !header.startsWith(HeaderConstant.TOKEN_BEARER_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authorization = authorizeRequest(request);
            SecurityContextHolder.getContext().setAuthentication(authorization);
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            try {
                RestExceptionMessage exceptionMessage;
                if (e instanceof EmployeeNotFoundException) {
                    exceptionMessage = createExceptionMessage(ErrorCodes.EMAIL_NOT_FOUND);
                } else if (e instanceof ExpiredJwtException) {
                    exceptionMessage = createExceptionMessage(ErrorCodes.TOKEN_EXPIRED);
                } else {
                    exceptionMessage = createExceptionMessage(ErrorCodes.TOKEN_NOT_VALID);
                }
                response.getWriter().write(new Gson().toJson(exceptionMessage));
            } catch (IOException ex) {
                throw new RuntimeException(e);
            }
        }
    }

    private RestExceptionMessage createExceptionMessage(int errorCode) {
        return RestExceptionMessage.builder()
                .timestamp(new Date())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error(new ErrorMessage(errorCode, "Error validating request"))
                .build();
    }

    private UsernamePasswordAuthenticationToken authorizeRequest(HttpServletRequest request) {
        Optional<String> optional = jwtUtil.extractTokenFromHeader(request);
        if (optional.isPresent()) {
            String token = optional.get();
            SecretKey secretKey = (SecretKey) jwtService.getTokenSecretKey(token);
            Claims claims = jwtUtil.decodeJWT(secretKey, token);
            String user = claims.getSubject();
            UserDetails userDetails = employeeService.loadUserByUsername(user);
            if (userDetails != null) {
                return new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
            }
        }
        return null;
    }
}
