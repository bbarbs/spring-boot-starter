package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class EmailNotFoundException extends RestApiException {

    public EmailNotFoundException(String s) {
        super(s);
    }

    public EmailNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
