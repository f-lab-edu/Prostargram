package flab.project.config.exception;

public class InputBadWordException extends RuntimeException {
    public InputBadWordException() {}

    public InputBadWordException(String message) {
        super(message);
    }
}