package com.slicequeue.inflearn.exception;

import com.slicequeue.inflearn.dto.BaseErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class BaseRuntimeException extends RuntimeException {

    private final Integer code;
    private final String message;
    private final Map<String, Object> detail;
    private final HttpStatus status;

    protected BaseRuntimeException(HttpStatus status, String message) {
        this.status = status;
        this.code = null;
        this.message = message;
        this.detail = null;
    }

    protected BaseRuntimeException(HttpStatus status, String message, Object[] args) {
        this.status = status;
        this.code = null;
        this.message = message;
        this.detail = null;
    }

    protected BaseRuntimeException(HttpStatus status, String message, Object[] args, Map<String, Object> detail) {
        this.status = status;
        this.code = null;
        this.message = message;
        this.detail = detail;
    }

    protected BaseRuntimeException(HttpStatus status, Integer code, String message, Object[] args) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.detail = null;

    }

    protected BaseRuntimeException(HttpStatus status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.detail = null;
    }

    public BaseErrorResponse toErrorResponse() {
        return new BaseErrorResponse(this.code, this.message, this.detail);
    }

    public HttpStatus getHttpStatus() {
        return status;
    }

}
