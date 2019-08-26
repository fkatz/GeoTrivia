package javatp.exception;

public class EntityContentRepeatedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityContentRepeatedException() {
        super();
    }

    public EntityContentRepeatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityContentRepeatedException(String message) {
        super(message);
    }

    public EntityContentRepeatedException(Throwable cause) {
        super(cause);
    }
}