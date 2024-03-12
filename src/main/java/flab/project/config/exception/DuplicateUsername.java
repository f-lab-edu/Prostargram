package flab.project.config.exception;

public class DuplicateUsername extends RuntimeException {
    public DuplicateUsername() {
    }

    public DuplicateUsername(String message) {
        super(message);
    }
}
