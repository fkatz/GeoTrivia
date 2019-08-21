package javatp.exception;

public class UsernameTakenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameTakenException() {
        super();
    }

    public UsernameTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameTakenException(String message) {
        super(message);
    }

    public UsernameTakenException(Throwable cause) {
        super(cause);
    } }