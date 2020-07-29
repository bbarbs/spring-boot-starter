package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class InvalidTokenException extends RestApiException {

    public InvalidTokenException(String s) {
        super(s);
    }

    public InvalidTokenException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
