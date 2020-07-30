package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class EmailNotFoundException extends RestApiException {

    public EmailNotFoundException(String s, int errorCode) {
        super(s, errorCode);
    }

    public EmailNotFoundException(String s, int errorCode, Throwable throwable) {
        super(s, errorCode, throwable);
    }
}
