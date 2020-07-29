package com.avocado.fruit.repository.impl;

import com.avocado.fruit.repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import java.util.Optional;

@Repository
public class JwtRepositoryImpl implements JwtRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public JwtRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setTokenSecretKey(String token, SecretKey secretKey) {
        redisTemplate.opsForValue().set(token, secretKey);
    }

    @Override
    public Optional<Object> getTokenSecretKey(String token) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(token));
    }

    @Override
    public void delete(String token) {
        redisTemplate.delete(token);
    }

}
