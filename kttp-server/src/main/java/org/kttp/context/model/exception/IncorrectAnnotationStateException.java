package org.kttp.context.model.exception;

public class IncorrectAnnotationStateException extends RuntimeException {

    public IncorrectAnnotationStateException() {
    }

    public IncorrectAnnotationStateException(String message) {
        super(message);
    }

    public IncorrectAnnotationStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
