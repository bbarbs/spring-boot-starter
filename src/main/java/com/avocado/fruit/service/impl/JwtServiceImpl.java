package com.avocado.fruit.service.impl;

import com.avocado.fruit.exception.InvalidTokenException;
import com.avocado.fruit.exception.config.ErrorCodes;
import com.avocado.fruit.repository.JwtRepository;
import com.avocado.fruit.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;

@Service
@Transactional
public class JwtServiceImpl implements JwtService {

    private final JwtRepository jwtRepository;

    @Autowired
    public JwtServiceImpl(JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    @Override
    public void setTokenSecretKey(String token, SecretKey secretKey) {
        jwtRepository.setTokenSecretKey(token, secretKey);
    }

    @Override
    public void deleteToken(String token) {
        jwtRepository.delete(token);
    }

    @Override
    public Object getTokenSecretKey(String token) {
        return jwtRepository.getTokenSecretKey(token).orElseThrow(() -> new InvalidTokenException("Invalid token", ErrorCodes.TOKEN_NOT_VALID));
    }
}
