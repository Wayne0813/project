package com.wayne.erp.exception;

/**
 * @author LV
 * @date 2018/7/26
 */
public class PermissionsException extends RuntimeException {

    public PermissionsException() {

    }

    public PermissionsException(String message) {
        super(message);
    }

    public PermissionsException(Throwable th) {
        super(th);
    }

    public PermissionsException(String message, Throwable th) {
        super(message, th);
    }

}
