package flab.project.config.exception;

public class NumberLimitOfPostHashTagExceededException extends RuntimeException {

    public NumberLimitOfPostHashTagExceededException() {
    }

    public NumberLimitOfPostHashTagExceededException(String message) {
        super(message);
    }
}