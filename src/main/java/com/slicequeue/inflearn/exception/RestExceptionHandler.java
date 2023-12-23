package com.slicequeue.inflearn.exception;

import com.slicequeue.inflearn.dto.BaseErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override // API 없는 주소 요청의 경우 404 관련
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                BaseErrorResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @Override // API 의 method 지원을 안하는 경우 415 관련
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                BaseErrorResponse.builder()
                        .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override // 요청 query param 에서 필수 값이 빠진 경우
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return new ResponseEntity<>(
                BaseErrorResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @Override // 요청 body 의 json 포멧에 오류가 있는 경우
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return new ResponseEntity<>(
                BaseErrorResponse.builder()
                        .code(ExceptionConstants.ERROR_RESPONSE_VALIDATION_FAIL)
                        .message("Invalid request body")
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @Override // 요청 인자 검증에 실패한 경우
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, Object> detail = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            detail.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(
                BaseErrorResponse.builder()
                        .code(ExceptionConstants.ERROR_RESPONSE_VALIDATION_FAIL)
                        .message("Validation failed")
                        .detail(detail)
                        .build(),
                HttpStatus.BAD_REQUEST);

    }

    @Override // query param 부분에 포멧이 맞지 않는 경우
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, Object> detail = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            Map<String, Object> descriptions = new HashMap<>();
            descriptions.put("rejectedValue", fieldError.getRejectedValue());
            detail.put(fieldError.getField(), descriptions);
        }

        return new ResponseEntity<>(
                BaseErrorResponse.builder()
                        .code(ExceptionConstants.ERROR_RESPONSE_VALIDATION_FAIL)
                        .message("Parameter Binding failed")
                        .detail(detail)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseRuntimeException.class) // BaseRuntimeException 공통 처리
    protected ResponseEntity<BaseErrorResponse> handleBaseRuntimeException(BaseRuntimeException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class) // 그 밖에 처리 못한 오류 응답 처리
    protected ResponseEntity<String> handleException(Exception ex) {
        log.error("RestResponseEntityExceptionHandler > Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
