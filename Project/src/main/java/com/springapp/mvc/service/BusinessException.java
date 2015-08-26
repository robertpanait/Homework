package com.springapp.mvc.service;

/**
 * Created by rpanait on 8/25/2015.
 */
public class BusinessException extends Exception{
    private String message;
    public BusinessException(String message){
        this.message=message;
    }


}
