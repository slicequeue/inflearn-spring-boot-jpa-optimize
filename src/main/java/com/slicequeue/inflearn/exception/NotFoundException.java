package com.slicequeue.inflearn.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class NotFoundException extends BaseRuntimeException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(status, message);
    }

    public NotFoundException(String message, Object[] args) {
        super(status, message, args);
    }

    public NotFoundException(String message, Object[] args, Map<String, Object> detail) {
        super(status, message, args, detail);
    }

    public NotFoundException(Integer code, String message, Object[] args) {
        super(status, code, message, args);
    }

    public NotFoundException(Integer code, String message) {
        super(status, code, message);
    }
}
