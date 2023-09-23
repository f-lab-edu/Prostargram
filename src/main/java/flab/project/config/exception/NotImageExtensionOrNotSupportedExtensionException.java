package flab.project.config.exception;

public class NotImageExtensionOrNotSupportedExtensionException extends RuntimeException {

    public NotImageExtensionOrNotSupportedExtensionException() {
    }

    public NotImageExtensionOrNotSupportedExtensionException(String message) {
        super(message);
    }
}