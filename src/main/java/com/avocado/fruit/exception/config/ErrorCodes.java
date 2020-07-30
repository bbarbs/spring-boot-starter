package com.avocado.fruit.exception.config;

public interface ErrorCodes {

    int ID_INCONSISTENT = 4000;

    int EMAIL_NOT_FOUND = 5000;
    int EMAIL_ALREADY_EXISTS = 5001;
    int EMPLOYEE_ID_NOT_FOUND = 5002;
    int ROLE_ID_NOT_FOUND = 5003;
    int TOKEN_NOT_VALID = 5004;
    int TOKEN_EXPIRED = 5005;
    int INVALID_EMAIL_OR_PASSWORD = 5006;
}
