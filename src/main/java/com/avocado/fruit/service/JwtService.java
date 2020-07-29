package com.avocado.fruit.service;

import javax.crypto.SecretKey;

public interface JwtService {

    void setTokenSecretKey(String token, SecretKey secretKey);

    void deleteToken(String token);

    Object getTokenSecretKey(String token);
}
