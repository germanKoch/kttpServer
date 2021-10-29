package org.kttp.model.exception;

public class InvalidHttpRequestException extends RuntimeException {
    public InvalidHttpRequestException() {
    }

    public InvalidHttpRequestException(String message) {
        super(message);
    }

    public InvalidHttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
