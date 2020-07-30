package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class ExpiredJwtException extends RestApiException {

    public ExpiredJwtException(String s, int errorCode) {
        super(s, errorCode);
    }

    public ExpiredJwtException(String s, int errorCode, Throwable throwable) {
        super(s, errorCode, throwable);
    }
}
