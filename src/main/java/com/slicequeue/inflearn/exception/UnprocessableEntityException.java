package com.slicequeue.inflearn.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnprocessableEntityException extends BaseRuntimeException {
    private static final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

    protected UnprocessableEntityException(String message) {
        super(status, message);
    }

    protected UnprocessableEntityException(String message, Object[] args) {
        super(status, message, args);
    }

    protected UnprocessableEntityException(String message, Object[] args, Map<String, Object> detail) {
        super(status, message, args, detail);
    }

    protected UnprocessableEntityException(Integer code, String message, Object[] args) {
        super(status, code, message, args);
    }

    protected UnprocessableEntityException(Integer code, String message) {
        super(status, code, message);
    }
}
