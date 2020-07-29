package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class BadRequestException extends RestApiException {

    public BadRequestException(String s) {
        super(s);
    }

    public BadRequestException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
