package com.slicequeue.inflearn.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends BaseRuntimeException {

    private static final HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(status, message);
    }

    public BadRequestException(String message, Object[] args) {
        super(status, message, args);
    }

    public BadRequestException(String message, Object[] args, Map<String, Object> detail) {
        super(status, message, args, detail);
    }

    public BadRequestException(Integer code, String message, Object[] args) {
        super(status, code, message, args);
    }

    public BadRequestException(Integer code, String message) {
        super(status, code, message);
    }
}
