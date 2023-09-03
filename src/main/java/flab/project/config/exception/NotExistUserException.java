package flab.project.config.exception;

public class NotExistUserException extends RuntimeException {

    public NotExistUserException() {
    }

    public NotExistUserException(String message) {
        super(message);
    }
}