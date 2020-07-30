package com.avocado.fruit.exception.config;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class RestApiException extends AuthenticationException {

    public int errorCode;

    public RestApiException(String s, int errorCode) {
        super(s);
        this.errorCode = errorCode;
    }

    public RestApiException(String s, int errorCode, Throwable throwable) {
        super(s, throwable);
        this.errorCode = errorCode;
    }
}
