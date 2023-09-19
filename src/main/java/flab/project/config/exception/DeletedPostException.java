package flab.project.config.exception;

public class DeletedPostException extends RuntimeException {

    public DeletedPostException() {
    }

    public DeletedPostException(String message) {
        super(message);
    }
}