package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class InvalidTokenException extends RestApiException {

    public InvalidTokenException(String s, int errorCode) {
        super(s, errorCode);
    }

    public InvalidTokenException(String s, int errorCode, Throwable throwable) {
        super(s, errorCode, throwable);
    }
}
