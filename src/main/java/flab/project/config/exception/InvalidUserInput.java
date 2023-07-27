package flab.project.config.exception;

public class InvalidUserInput extends RuntimeException {
    public InvalidUserInput() {}

    public InvalidUserInput(String message) {
        super(message);
    }
}
