package com.slicequeue.inflearn.exception;

import org.springframework.http.HttpStatus;

public class ExceptionConstants {
    // 400 관련 - BAD_REQUEST
    public static final Integer ERROR_RESPONSE_VALIDATION_FAIL = 400001;

    // 422 관련 - UNPROCESSABLE_ENTITY 관련
    public static final Integer ERROR_UNPROCESSABLE_ENTITY_WRONG_VALUE = 422001;
}
