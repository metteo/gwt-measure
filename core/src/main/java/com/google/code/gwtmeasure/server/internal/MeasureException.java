package com.google.code.gwtmeasure.server.internal;

public class MeasureException extends RuntimeException {

    public MeasureException(String message) {
        super(message);
    }

    public MeasureException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeasureException(Throwable cause) {
        super(cause);
    }

}
