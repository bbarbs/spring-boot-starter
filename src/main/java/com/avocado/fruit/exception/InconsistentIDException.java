package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class InconsistentIDException extends RestApiException {

    public InconsistentIDException(String s, int errorCode) {
        super(s, errorCode);
    }

    public InconsistentIDException(String s, int errorCode, Throwable throwable) {
        super(s, errorCode, throwable);
    }
}
