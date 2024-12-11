package flab.project.domain.user.exception;

public class AlreadyFollowException extends RuntimeException{

    public AlreadyFollowException(String message) {
        super(message);
    }
}
