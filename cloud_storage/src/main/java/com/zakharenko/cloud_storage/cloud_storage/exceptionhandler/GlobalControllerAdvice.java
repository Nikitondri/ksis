package com.zakharenko.cloud_storage.cloud_storage.exceptionhandler;

import com.zakharenko.cloud_storage.cloud_storage.exception.InvalidPasswordException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    public ResponseEntity<ExceptionInfo> handleInvalidPasswordException(InvalidPasswordException exception){
        ExceptionInfo info = new ExceptionInfo(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(info, HttpStatus.UNAUTHORIZED);
    }
}
