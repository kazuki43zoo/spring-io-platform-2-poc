package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler { // (1)

    private final Map<Class<? extends Exception>, String> messageMappings =
            Collections.unmodifiableMap(new LinkedHashMap() {{
                put(HttpMessageNotReadableException.class, "Request body is invalid");
                put(MethodArgumentNotValidException.class, "Request value is invalid");
                put(BindException.class, "Request parameter value is invalid");
            }});

    private ApiError createApiError(Exception ex, String defaultMessage) {
        ApiError apiError = new ApiError();
        apiError.setMessage(resolveMessage(ex, defaultMessage));
        apiError.setDocumentationUrl("http:://example.com/api/errors");
        return apiError;
    }

    @Autowired
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ApiError apiError = createApiError(ex, ex.getMessage()); // (1)
        return super.handleExceptionInternal(
                ex, apiError, headers, status, request); // (2)
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleBookNotFoundException(
            BookNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(
                ex, null, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleSystemException(
            Exception ex, WebRequest request) {
        ApiError apiError = createApiError(ex, "System error is occurred"); // (1)
        return super.handleExceptionInternal(
                ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        // (1)
        ApiError apiError = createApiError(ex, ex.getMessage());
        // (2)
        ex.getBindingResult().getGlobalErrors().stream()
                .forEach(error -> apiError.addDetail(
                        error.getObjectName(),
                        messageSource.getMessage(error, request.getLocale())));
        // (3)
        ex.getBindingResult().getFieldErrors().stream()
                .forEach(error -> apiError.addDetail(
                        error.getField(),
                        messageSource.getMessage(error, request.getLocale())));
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    private String resolveMessage(Exception ex, String defaultMessage) {
        return messageMappings.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(ex.getClass()))
                .findFirst()
                .map(entry -> entry.getValue())
                .orElse(defaultMessage);
    }

}