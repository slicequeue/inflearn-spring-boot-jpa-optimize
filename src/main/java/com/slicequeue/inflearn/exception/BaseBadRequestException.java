package com.slicequeue.inflearn.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BaseBadRequestException extends BaseRuntimeException {

    private static final HttpStatus status = HttpStatus.BAD_REQUEST;

    protected BaseBadRequestException(String message) {
        super(status, message);
    }

    protected BaseBadRequestException(String message, Object[] args) {
        super(status, message, args);
    }

    protected BaseBadRequestException(String message, Object[] args, Map<String, Object> detail) {
        super(status, message, args, detail);
    }

    protected BaseBadRequestException(Integer code, String message, Object[] args) {
        super(status, code, message, args);
    }

    protected BaseBadRequestException(Integer code, String message) {
        super(status, code, message);
    }
}
