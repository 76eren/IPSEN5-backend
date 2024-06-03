package com.cgi.ipsen5.Exception;

public class BuildingNotFoundException extends RuntimeException {
    public BuildingNotFoundException(String message) {
        super(message);
    }

    public BuildingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildingNotFoundException(Throwable cause) {
        super(cause);
    }
}
