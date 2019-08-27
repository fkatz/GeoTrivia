package javatp.exception;

public class DuplicatedCorrectAnswerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicatedCorrectAnswerException() {
        super();
    }

    public DuplicatedCorrectAnswerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedCorrectAnswerException(String message) {
        super(message);
    }

    public DuplicatedCorrectAnswerException(Throwable cause) {
        super(cause);
    } }