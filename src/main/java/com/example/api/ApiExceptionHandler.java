package com.example.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
            }});

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ApiError apiError = new ApiError(); // (2)
        apiError.setMessage(resolveMessage(ex));
        apiError.setDocumentationUrl("http:://example.com/api/errors");

        return super.handleExceptionInternal(ex, apiError, headers, status, request); // (3)
    }

    private String resolveMessage(Exception ex) {
        return messageMappings
                .entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(ex.getClass()))
                .findFirst()
                .map(entry -> entry.getValue())
                .orElse(ex.getMessage());
    }

}