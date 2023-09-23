package flab.project.config.exception;

public class NumberLimitOfInterestExceededException extends RuntimeException {

    public NumberLimitOfInterestExceededException() {
    }

    public NumberLimitOfInterestExceededException(String message) {
        super(message);
    }
}