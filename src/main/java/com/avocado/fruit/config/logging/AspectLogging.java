package com.avocado.fruit.config.logging;

import com.avocado.fruit.service.JwtService;
import com.avocado.fruit.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Component
@Aspect
public class AspectLogging {

    private final JwtService jwtService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AspectLogging(JwtService jwtService, JwtUtil jwtUtil) {
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }

    @Before(
            "execution(* org.springframework.security.web.authentication.logout.LogoutSuccessHandler.onLogoutSuccess(..))"
    )
    public void logWhenUserLogout(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
        String username = null;
        try {
            HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
            Optional<String> optional = jwtUtil.extractTokenFromHeader(request);
            if(optional.isPresent()) {
                String token = optional.get();
                SecretKey secretKey = (SecretKey) jwtService.getTokenSecretKey(token);
                Claims claims = jwtUtil.decodeJWT(secretKey, token);
                username = claims.getSubject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("Logging out: " + "user=" + username + "; " + "date=" + new Date().getTime());
        }
    }
}
