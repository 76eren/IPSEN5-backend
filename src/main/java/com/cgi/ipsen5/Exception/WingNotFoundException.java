package com.cgi.ipsen5.Exception;

public class WingNotFoundException extends RuntimeException{
    public WingNotFoundException(String message) {
        super(message);
    }

    public WingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WingNotFoundException(Throwable cause) {
        super(cause);
    }
}
