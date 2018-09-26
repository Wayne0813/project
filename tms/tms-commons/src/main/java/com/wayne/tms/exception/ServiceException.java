package com.wayne.tms.exception;

/**
 * @author LV
 * @date 2018/8/31
 */
public class ServiceException extends RuntimeException {

    public ServiceException(){}

    public ServiceException(String message){
        super(message);
    }
    public ServiceException(Throwable throwable){
        super(throwable);
    }
    public ServiceException(Throwable throwable, String message){
        super(message, throwable);
    }

}
