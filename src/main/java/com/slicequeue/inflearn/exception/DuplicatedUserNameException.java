package com.slicequeue.inflearn.exception;

import static com.slicequeue.inflearn.exception.ExceptionConstants.ERROR_RESPONSE_DUPLICATED_NAME_USER;

public class DuplicatedUserNameException extends BadRequestException {

    public DuplicatedUserNameException(String message) {
        super(ERROR_RESPONSE_DUPLICATED_NAME_USER, String.format("사용자 이름이 중복되었습니다. 이름: %s", message));
    }
}
