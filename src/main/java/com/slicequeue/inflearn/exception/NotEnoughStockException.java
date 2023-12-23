package com.slicequeue.inflearn.exception;

import static com.slicequeue.inflearn.exception.ExceptionConstants.ERROR_UNPROCESSABLE_ENTITY_WRONG_VALUE;

public class NotEnoughStockException extends UnprocessableEntityException {

    public NotEnoughStockException(String message) {
        super(ERROR_UNPROCESSABLE_ENTITY_WRONG_VALUE, message);
    }
}
