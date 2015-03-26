package com.yazuo.superwifi.exception;

public class BussinessException extends RuntimeException {

    private static final long serialVersionUID = 477399363086602984L;

    public BussinessException() {
        super("系统内部异常，请联系雅座人员");
    }


    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
