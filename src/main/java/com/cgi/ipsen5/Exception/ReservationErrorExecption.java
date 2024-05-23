package com.cgi.ipsen5.Exception;

public class ReservationErrorExecption extends RuntimeException{
    public ReservationErrorExecption(String message) {
        super(message);
    }

    public ReservationErrorExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationErrorExecption(Throwable cause) {
        super(cause);
    }
}
