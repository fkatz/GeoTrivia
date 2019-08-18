package javatp.exception;

public class IncompleteObjectException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncompleteObjectException() {
        super();
    }

    public IncompleteObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompleteObjectException(String message) {
        super(message);
    }

    public IncompleteObjectException(Throwable cause) {
        super(cause);
    } }