package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class InconsistentIDException extends RestApiException {

    public InconsistentIDException(String s) {
        super(s);
    }

    public InconsistentIDException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
