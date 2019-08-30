package javatp.exception;

public class NoQuestionsInPOIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoQuestionsInPOIException() {
        super();
    }

    public NoQuestionsInPOIException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoQuestionsInPOIException(String message) {
        super(message);
    }

    public NoQuestionsInPOIException(Throwable cause) {
        super(cause);
    } }