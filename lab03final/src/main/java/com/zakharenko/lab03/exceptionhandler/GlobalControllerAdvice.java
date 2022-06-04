package com.zakharenko.lab03.exceptionhandler;

import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.exception.AccessException;
import com.zakharenko.lab03.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessException.class)
    public String handleAccessException(){
        return "error/403";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginException.class)
    public String handleLoginException(Model model){
        model.addAttribute("error", "Incorrect login or password");
        model.addAttribute("user", new User());
        return "login";
    }

}
