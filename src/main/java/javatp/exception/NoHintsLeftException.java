package javatp.exception;

public class NoHintsLeftException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoHintsLeftException() {
        super();
    }

    public NoHintsLeftException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoHintsLeftException(String message) {
        super(message);
    }

    public NoHintsLeftException(Throwable cause) {
        super(cause);
    } }