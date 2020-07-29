package com.avocado.fruit.repository;

import javax.crypto.SecretKey;
import java.util.Optional;

public interface JwtRepository {

    void setTokenSecretKey(String token, SecretKey secretKey);

    Optional<Object> getTokenSecretKey(String token);

    void delete(String token);
}
