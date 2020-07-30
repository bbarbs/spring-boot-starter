package com.avocado.fruit.exception.handler;

import com.avocado.fruit.exception.config.ErrorMessage;
import com.avocado.fruit.exception.config.RestApiException;
import com.avocado.fruit.exception.config.RestExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public abstract class AbstractExceptionResponse {

    private static final Logger logger = LoggerFactory.getLogger(AbstractExceptionResponse.class);

    protected ResponseEntity<RestExceptionMessage> errorResponse(RestApiException exception, HttpStatus status) {
        if (null != exception) {
            logger.error("Error caught: " + exception.getLocalizedMessage(), exception);
            return response(new RestExceptionMessage(
                            new Date(),
                            status.value(),
                            status,
                            new ErrorMessage(exception.getErrorCode(), exception.getLocalizedMessage()))
                    , status);
        } else {
            logger.error("Unknown error caught in RestExceptionHandler, {}", status);
            return response(null, status);
        }
    }

    private <T> ResponseEntity<T> response(T body, HttpStatus status) {
        logger.debug("Responding with a status of {}", status);
        return new ResponseEntity<>(body, status);
    }
}
