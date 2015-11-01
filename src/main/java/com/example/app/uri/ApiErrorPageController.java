package com.example.app.uri;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RequestMapping("error")
@RestController
public class ApiErrorPageController {

    @RequestMapping(params = "by=exceptionType")
    public ApiError handleErrorByExceptionType(HttpServletRequest request) {

        Exception ex = (Exception) request.getAttribute(
                RequestDispatcher.ERROR_EXCEPTION);
        if (ex == null) {
            return null;
        }

        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setDocumentationUrl("http://example.com/api/errors");

        return apiError;
    }

    @RequestMapping(params = "by=statusCode")
    public ApiError handleErrorByStatusCode(HttpServletRequest request) {

        Integer statusCode = (Integer) request.getAttribute(
                RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return null;
        }

        ApiError apiError = new ApiError();
        if (Arrays.asList(HttpStatus.values()).stream()
                .anyMatch(status -> status.value() == statusCode)) {
            apiError.setMessage(HttpStatus.valueOf(statusCode).getReasonPhrase());
        } else {
            apiError.setMessage("Custom error(" + statusCode + ") is occurred");
        }
        apiError.setDocumentationUrl("http://example.com/api/errors");

        return apiError;
    }


    @RequestMapping
    public ApiError handleError(HttpServletRequest request) {
        ApiError apiError = handleErrorByExceptionType(request);
        if (apiError == null) {
            apiError = handleErrorByStatusCode(request);
        }
        return apiError;
    }

    @RequestMapping(params = {"by=exceptionType", "httpStatus"})
    public ResponseEntity<ApiError> handleErrorByExceptionType(
            HttpServletRequest request,
            @RequestParam(defaultValue = "INTERNAL_SERVER_ERROR") HttpStatus httpStatus) {

        Exception ex = (Exception) request.getAttribute(
                RequestDispatcher.ERROR_EXCEPTION);
        if (ex == null) {
            return null;
        }

        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setDocumentationUrl("http://example.com/api/errors");

        return ResponseEntity.status(httpStatus).body(apiError);
    }

}
