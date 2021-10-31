package org.kttp.context.model.exception;

public class HandlersIntializationException extends RuntimeException {
    public HandlersIntializationException() {
    }

    public HandlersIntializationException(String message) {
        super(message);
    }

    public HandlersIntializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
