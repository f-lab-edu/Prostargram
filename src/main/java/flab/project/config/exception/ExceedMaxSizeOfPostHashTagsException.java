package flab.project.config.exception;

public class ExceedMaxSizeOfPostHashTagsException extends RuntimeException {

    public ExceedMaxSizeOfPostHashTagsException() {
    }

    public ExceedMaxSizeOfPostHashTagsException(String message) {
        super(message);
    }
}