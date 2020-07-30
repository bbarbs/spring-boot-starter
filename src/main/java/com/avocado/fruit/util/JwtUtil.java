package com.avocado.fruit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    @Value("${spring.application.name}")
    private String appName;

    public String createToken(@NotNull String subject, long expirationInMillis, @NotNull SecretKey secretKey) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(appName)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, secretKey);

        if (expirationInMillis > 0) {
            long expMillis = nowMillis + expirationInMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);
        }
        return jwtBuilder.compact();
    }

    public Claims decodeJWT(SecretKey key, String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public Optional<String> extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HeaderConstant.AUTHORIZATION_HEADER_STRING);
        if (authHeader != null) {
            if (authHeader.startsWith(HeaderConstant.TOKEN_BEARER_PREFIX)) {
                return Optional.of(authHeader.replace(HeaderConstant.TOKEN_BEARER_PREFIX, ""));
            }
        }
        return Optional.empty();
    }

    public SecretKey generateKey() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance("AES").generateKey();
    }

    public SecretKey retainKey(byte[] decodedKey) {
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
