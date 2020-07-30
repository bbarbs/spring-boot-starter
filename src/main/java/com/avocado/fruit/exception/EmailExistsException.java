package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class EmailExistsException extends RestApiException {

    public EmailExistsException(String s, int errorCode) {
        super(s, errorCode);
    }

    public EmailExistsException(String s, int errorCode, Throwable throwable) {
        super(s, errorCode, throwable);
    }
}
