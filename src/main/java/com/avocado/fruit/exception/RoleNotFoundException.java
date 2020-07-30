package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class RoleNotFoundException extends RestApiException {

    public RoleNotFoundException(String s, int errorCode) {
        super(s, errorCode);
    }

    public RoleNotFoundException(String s, int errorCode, Throwable throwable) {
        super(s, errorCode, throwable);
    }
}
