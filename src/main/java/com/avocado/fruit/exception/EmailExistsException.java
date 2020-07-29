package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class EmailExistsException extends RestApiException {

    public EmailExistsException(String s) {
        super(s);
    }

    public EmailExistsException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
