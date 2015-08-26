package com.springapp.mvc.controller;

import com.springapp.mvc.service.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * Created by rpanait on 8/25/2015.
 */
@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public String handleException(BusinessException be){
        return be.getMessage();
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public String handleIoException(BusinessException be){
        return be.getMessage();
    }
}
