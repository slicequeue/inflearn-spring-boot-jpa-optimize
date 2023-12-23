package com.slicequeue.inflearn.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BaseUnprocessableEntityException extends BaseRuntimeException {
    private static final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

    protected BaseUnprocessableEntityException(String message) {
        super(status, message);
    }

    protected BaseUnprocessableEntityException(String message, Object[] args) {
        super(status, message, args);
    }

    protected BaseUnprocessableEntityException(String message, Object[] args, Map<String, Object> detail) {
        super(status, message, args, detail);
    }

    protected BaseUnprocessableEntityException(Integer code, String message, Object[] args) {
        super(status, code, message, args);
    }

    protected BaseUnprocessableEntityException(Integer code, String message) {
        super(status, code, message);
    }
}
