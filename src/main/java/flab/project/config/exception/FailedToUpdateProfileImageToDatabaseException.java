package flab.project.config.exception;

public class FailedToUpdateProfileImageToDatabaseException extends RuntimeException {

    public FailedToUpdateProfileImageToDatabaseException() {
    }

    public FailedToUpdateProfileImageToDatabaseException(String message) {
        super(message);
    }
}