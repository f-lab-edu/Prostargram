package flab.project.config.exception;

public class InvalidUserInputException extends RuntimeException {

    public InvalidUserInputException() {}

    public InvalidUserInputException(String message) {
        super(message);
    }
}