package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class ExpiredJwtException extends RestApiException {

    public ExpiredJwtException(String s) {
        super(s);
    }

    public ExpiredJwtException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
