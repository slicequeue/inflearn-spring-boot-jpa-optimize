package com.slicequeue.inflearn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseErrorResponse {

    private Integer code;

    private String message;

    private Map<String, Object> detail;

    public BaseErrorResponse() {}

    @Builder
    public BaseErrorResponse(Integer code, String message, Map<String, Object> detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }
}
