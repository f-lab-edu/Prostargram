package flab.project.domain.user.exception;

public class InvalidVerificationCodeException extends RuntimeException {

    public InvalidVerificationCodeException() {
        super("인증 코드가 일치하지 않습니다.");
    }
}
