package com.example.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.terasoluna.gfw.common.exception.SystemException;

@ControllerAdvice(basePackageClasses = ExceptionHandlerControllerAdvice.class)
public class ExceptionHandlerControllerAdvice {


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleSystemException(SystemException e, HandlerMethod handlerMethod) {
        return e.getClass().getSimpleName() + " is occurred in " + handlerMethod.toString();
    }

}
