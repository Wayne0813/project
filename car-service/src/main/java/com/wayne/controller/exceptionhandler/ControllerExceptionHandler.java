package com.wayne.controller.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * @author LV
 * @date 2018/7/24
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IOException.class)
    public String notFoundException(){
        return "error/500";
    }

}
